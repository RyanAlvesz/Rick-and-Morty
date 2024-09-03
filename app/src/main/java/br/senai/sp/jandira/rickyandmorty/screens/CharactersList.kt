package br.senai.sp.jandira.rickyandmorty.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CharactersList(modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xffdddddd)
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Rick & Morty API")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun CharactersListPreview() {
    CharactersList()
}