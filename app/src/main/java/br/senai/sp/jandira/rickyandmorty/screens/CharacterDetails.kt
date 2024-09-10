package br.senai.sp.jandira.rickyandmorty.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickyandmorty.model.Character
import br.senai.sp.jandira.rickyandmorty.model.Result
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickAndMortyTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterDetails(navigationController: NavHostController, characterId : String) {

    var character by remember{
        mutableStateOf(Character())
    }

    // Efetuar chamada para API
    val callCharacter = RetrofitFactory()
        .getCharacterService()
        .getCharacterById(characterId.toInt())

    callCharacter.enqueue(object : Callback<Character> {
        override fun onResponse(p0: Call<Character>, response: Response<Character>) {
            character = response.body()!!
        }
        override fun onFailure(p0: Call<Character>, p1: Throwable) {
        }
    })

    Surface (modifier = Modifier
        .fillMaxSize(),
        color = Color(0xff122345)
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {

            Card (
                modifier = Modifier
                    .size(80.dp),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = character.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = character.status, color = Color.White)
            Text(text = character.species, color = Color.White)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GetCharacterPreview() {
    RickAndMortyTheme {
    }
}