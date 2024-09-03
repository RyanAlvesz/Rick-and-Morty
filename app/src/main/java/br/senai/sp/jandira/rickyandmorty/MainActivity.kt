package br.senai.sp.jandira.rickyandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.senai.sp.jandira.rickyandmorty.screens.CharacterDetails
import br.senai.sp.jandira.rickyandmorty.screens.CharactersList
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickAndMortyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
//                CharacterDetails()
                CharactersList()
            }
        }
    }
}



