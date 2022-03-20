package com.example.movierow

import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movierow.ui.theme.MovieRowTheme
import models.Movie
import models.getMovies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieRowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //MainContent(movieList = getMovies())
                    TopBar { MainContent(movieList = getMovies())}
                }
            }
        }
    }
}

@Composable
fun TopBar(content: @Composable () -> Unit){
    var showMenu by remember{
        mutableStateOf(false)}
    MovieRowTheme() {
        Scaffold(
            topBar = {
                TopAppBar(title = {Text(text="Movies")},
                    actions = {
                        IconButton(onClick = { showMenu =! showMenu })
                        {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false })
                        {
                            DropdownMenuItem(onClick = { /*TODOLATER*/ })
                            {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "fav icon",
                                        modifier = Modifier.padding(2.dp))
                                    Text(text ="Favourites", style= MaterialTheme.typography.h6,
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .width(120.dp))
                                }
                            }
                        }
                    })
            }
        )
        {
            content()
        }
    }
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieRow(movie: Movie ) {
    var showDetails by remember{
        mutableStateOf(false)}
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    )

    {
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Surface(
                modifier = Modifier
                    .size(130.dp)
                    .padding(12.dp),
                elevation = 6.dp
            )
            {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "movie pic")
            }

            Column() {
                Text(text = "${movie.title}",style = MaterialTheme.typography.h6)
                Text(text = "Director: ${movie.director}")
                Text(text = "Released: ${movie.year}")
                AnimatedVisibility(visible = showDetails, enter = fadeIn(), exit = slideOutHorizontally() + shrinkVertically() + fadeOut())
                {
                    MovieDetails(movie)
                }
                when(showDetails)
                {
                    true ->Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "arrow down", modifier = Modifier.clickable { showDetails =! showDetails  })
                    false-> Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "arrow up", modifier = Modifier.clickable { showDetails =! showDetails  })
                }
            }


        }

    }

    }
@Composable
fun MovieDetails(movie: Movie){
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(4.dp))
    {
        Column() {
            Text(text ="Plot: ${movie.plot}", style= MaterialTheme.typography.body2)
            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top =2.dp, bottom = 2.dp))
            Text(text ="Genre: ${movie.genre}", style= MaterialTheme.typography.body2)
            Text(text ="Actor: ${movie.actors}", style= MaterialTheme.typography.body2)
            Text(text ="Rating: ${movie.rating}", style= MaterialTheme.typography.body2)
        }

    }

}

@Composable
fun MainContent(movieList: List<Movie>){
    LazyColumn {
        items(movieList){
            movieTitle -> MovieRow(movieTitle)
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieRowTheme {
        MovieRow()
    }
}*/
