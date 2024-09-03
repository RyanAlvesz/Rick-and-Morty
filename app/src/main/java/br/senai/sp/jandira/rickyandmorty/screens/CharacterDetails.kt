package br.senai.sp.jandira.rickyandmorty.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.rickyandmorty.model.Character
import br.senai.sp.jandira.rickyandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickyandmorty.ui.theme.RickAndMortyTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterDetails() {

    var id by remember{
        mutableStateOf("")
    }

    var character by remember{
        mutableStateOf(Character())
    }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = id,
            onValueChange = {
                id = it
            },
            label = { Text(text = "Digite o id do personagem") }
        )
        Button(
            onClick = {

                val callCharacter = RetrofitFactory()
                    .getCharacterService()
                    .getCharacterById(id.toInt())

                callCharacter.enqueue(object : Callback<Character> {
                    override fun onResponse(p0: Call<Character>, response: Response<Character>) {
                        character = response.body()!!
                    }

                    override fun onFailure(p0: Call<Character>, p1: Throwable) {
                    }

                })

            },
            modifier = Modifier.width(280.dp)
        ) {
            Text(text = "Buscar personagem")
        }
        Text(text = "Nome: ${character.name}")
        Text(text = "Status: ${character.status}")
        Text(text = "Espécie: ${character.species}")
        Text(text = "Origem: ${character.origin?.name}")
        Text(text = "Localização: ${character.location?.name}")
    }
}

@Preview(showBackground = true)
@Composable
fun GetCharacterPreview() {
    RickAndMortyTheme {
        CharacterDetails()
    }
}