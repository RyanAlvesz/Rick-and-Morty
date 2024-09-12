package br.senai.sp.jandira.rickyandmorty.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickyandmorty.model.Character
import br.senai.sp.jandira.rickyandmorty.model.Episode
import br.senai.sp.jandira.rickyandmorty.model.EpisodesList
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
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

    var episodesIdList: String = "";

    character.episode.forEach(){
        episodesIdList += "${it.substringAfterLast("/")},"
    }

    var episodesList by remember {
        mutableStateOf(listOf<Episode>())
    }

    // Efetuar chamada para API
    val callEpisodes = RetrofitFactory()
        .getEpisodeService()
        .getEpisodesByCharacter(episodesIdList.dropLast(1))
    val url = callEpisodes.request().url.toString()
    Log.i("url", "CharacterDetails: $url")


    callEpisodes.enqueue(object : Callback<List<Episode>> {
        override fun onResponse(p0: Call<List<Episode>>, response: Response<List<Episode>>) {
            episodesList = response.body()!!
        }

        override fun onFailure(p0: Call<List<Episode>>, p1: Throwable) {
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
                    .size(120.dp),
                border = BorderStroke(2.dp, Color(0xff60a85f)),
                shape = CircleShape,
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = character.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 32.sp,
                    shadow = Shadow(
                        color = Color(0xff60a85f), offset = Offset(-3.0f, 3.0f)
                    )
                ),
            )
            Text(text = character.origin.name, color = Color.White, fontSize = 16.sp)
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                Card (
                    modifier = Modifier
                        .padding(end = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xff60a85f)
                    )
                ) {
                    Text(
                        text = character.species,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                    )
                }
                Card (
                    modifier = Modifier
                        .padding(end = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xff60a85f)
                    )
                ) {
                    Text(
                        text = character.gender,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                    )
                }
                Card (
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xff60a85f)
                    )
                ) {
                    Text(
                        text = character.status,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
            )  {

                Text(
                    text = "Lista de episódios",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn {
                    items(episodesList){
                        EpisodeCard(it)
                    }
                }

            }

        }

    }

}

@Composable
fun EpisodeCard(episode: Episode) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
            hoveredElevation = 36.dp
        ),
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff60a85f)),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column (
                modifier = Modifier
                    .weight(4f,true)
            ) {
                Text(
                    text = episode.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = episode.episode,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = episode.airDate,
                color = Color.White,
                modifier = Modifier
                    .weight(2f, true)
            )
        }
    }

}