package com.example.blooddonation
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.blooddonation.databinding.ActivityHomeElementContainerBinding


class DashboardElementContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeElementContainerBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeElementContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        if (intent.getStringExtra("frag").equals("previousBloodRequest")) {
            val navGraph = navController.navInflater.inflate(R.navigation.dashboard_nav_graph)
            navGraph.setStartDestination(R.id.fragment_requests)
            navController.graph = navGraph
        }else if(intent.getStringExtra("frag").equals("viewBloodRequest")) {
            val navGraph = navController.navInflater.inflate(R.navigation.dashboard_nav_graph)
            navGraph.setStartDestination(R.id.fragment_give_blood)
            navController.graph = navGraph
        }else if(intent.getStringExtra("frag").equals("addBloodRequest")) {
            val navGraph = navController.navInflater.inflate(R.navigation.dashboard_nav_graph)
            navGraph.setStartDestination(R.id.fragment_find_donor)
            navController.graph = navGraph
        }

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}