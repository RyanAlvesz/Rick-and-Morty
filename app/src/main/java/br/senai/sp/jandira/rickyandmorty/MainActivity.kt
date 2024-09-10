package br.senai.sp.jandira.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.jandira.rickyandmorty.screens.*
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickAndMortyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {

                val navigationController = rememberNavController()

                NavHost(navController = navigationController, startDestination = "charactersList") {
                    composable(route = "charactersList"){
                        CharactersList(navigationController)
                    }
                    composable(route = "characterDetails/{id}", arguments = listOf(
                        navArgument("id"){
                            type = NavType.StringType
                            defaultValue = ""
                            nullable = false
                        }
                    )){
                        CharacterDetails(navigationController, characterId = it.arguments?.getString("id") ?: "")
                    }
                }
            }
        }
    }
}



