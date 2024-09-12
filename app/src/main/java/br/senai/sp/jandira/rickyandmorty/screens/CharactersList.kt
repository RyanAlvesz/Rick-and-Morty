package br.senai.sp.jandira.rickyandmorty.screens

import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickyandmorty.model.Character
import br.senai.sp.jandira.rickyandmorty.model.Episode
import br.senai.sp.jandira.rickyandmorty.model.Result
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharactersList(navigationController: NavHostController) {

    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    // Efetuar chamada para API
    val callCharacterList = RetrofitFactory()
        .getCharacterService().getAllCharacters()

    callCharacterList.enqueue(object : Callback<Result>{
        override fun onResponse(p0: Call<Result>, response: Response<Result>) {
            characterList = response.body()!!.results
        }
        override fun onFailure(p0: Call<Result>, p1: Throwable) {
        }
    })

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xff043c6e)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Rick & Morty API",
                color = Color.White,
                style = TextStyle(
                    fontSize = 32.sp,
                    shadow = Shadow(
                        color = Color(0xff60a85f), offset = Offset(3.0f, 3.0f)
                    )
                ),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 18.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn {
                items(characterList){
                    CharacterCard(character = it, navigationController)
                }
            }

        }
    }
}

@Composable
fun CharacterCard(character: Character, navigationController: NavHostController) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
            hoveredElevation = 36.dp
        ),
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable { navigationController.navigate("characterDetails/${character.id}") },
            colors = CardDefaults.cardColors(containerColor = Color(0xff022545)),
        ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Card (
                border = BorderStroke(2.dp, Color(0xff60a85f)),
                modifier = Modifier
                    .size(76.dp),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop
                )
            }
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = character.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = character.species, color = Color.White)
            }
        }
    }
}