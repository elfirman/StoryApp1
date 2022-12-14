package com.elfirman.storyapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.elfirman.storyapp.ui.auth.AuthActivity
import com.elfirman.storyapp.ui.main.MainActivity
import com.elfirman.storyapp.ui.main.MainActivity.Companion.EXTRA_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        determineUserDirection()
    }


    private fun determineUserDirection() {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getAuthToken().collect { token ->
                    if (token.isNullOrEmpty()) {
                        // No token in data source, go to AuthActivity
                        Intent(this@SplashActivity, AuthActivity::class.java).also { intent ->
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        // Token detected on data source, go to MainActivity
                        Intent(this@SplashActivity, MainActivity::class.java).also { intent ->
                            intent.putExtra(EXTRA_TOKEN, token)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}