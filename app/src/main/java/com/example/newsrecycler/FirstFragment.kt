package com.example.newsrecycler

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*


class FirstFragment : Fragment(), NewsItemClicked {

    //private lateinit var viewModel: FirstFragmentViewModel //TODO Not yet Implemented

    lateinit var url: String
    lateinit var mAdapter: NewsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_first, container, false)



//        //ViewModel
//        Log.i("FirstFragment", "Called ViewModelProviders.of!")
//        viewModel = ViewModelProviders.of(this).get(FirstFragmentViewModel::class.java)
        mAdapter = NewsListAdapter(this)

        view.recyclerView.layoutManager = LinearLayoutManager(context) //Idhar GridLayoutManager, StaggeredLayoutManager ye sab bhi hota hai

        val items = fetchData()
        view.recyclerView.adapter = mAdapter



        // Setup refresh listener which triggers new data loading

        view.swipeContainer.setOnRefreshListener {
            mAdapter.clear()
            fetchTimelineAsync()
        }
//         Configure the refreshing colors
        view.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);


        view.toAnother.setOnClickListener {
//            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(22)
//            Navigation.findNavController(it).navigate(action)

            val action = FirstFragmentDirections.actionFirstFragmentToSettingsFragment()
            Navigation.findNavController(it).navigate(action)}

        //Isse three button menu aa jayega
        setHasOptionsMenu(true)

        return view
    }

    fun fetchTimelineAsync() {
        fetchData()
        swipeContainer.setRefreshing(false)
    }


    fun keepRefreshing() {
        Thread.sleep(1000)
        fetchData()
    }

    fun getData(): String {
        val sharedPreferences = activity?.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        val country = sharedPreferences?.getString("sharedCountry", "in")
        if (country == null) {
            return SettingsFragment.nat_code
        } else
            return country
    }

    fun fetchData() {
        url = "https://newsapi.org/v2/top-headlines?country="+getData()+"&apiKey=eb4b2f06446646778b0ad91a33b33b24"

        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }

                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                keepRefreshing()
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }


    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val  customTabsIntent = builder.build()
        context?.let { customTabsIntent.launchUrl(it, Uri.parse(item.url)) }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.secondFragmentOverflow -> {
                Navigation.findNavController(requireView())
                    .navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(22))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }







}