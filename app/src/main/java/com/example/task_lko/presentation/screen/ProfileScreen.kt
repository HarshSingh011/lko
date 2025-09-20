package com.example.task_lko.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.task_lko.domain.model.UserProfile
import com.example.task_lko.presentation.model.ProfileUiState
import com.example.task_lko.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val state = uiState

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is ProfileUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ProfileUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.retry() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ProfileUiState.Success -> {
                    DecorativeProfileScreen(profile = state.profile, onRetry = { viewModel.retry() })
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Something went wrong", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = message, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun DecorativeProfileScreen(profile: UserProfile, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF3B2D8A), Color(0xFF6A3BE0))
                    )
                )
        ) {
            
            Text(
                text = profile.username,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 12.dp)
            )
        }

        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .offset(y = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Surface(
                shape = CircleShape,
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(110.dp)
                    .zIndex(2f)
            ) {
                Box(modifier = Modifier.padding(4.dp)) {
                    AsyncImage(
                        model = profile.avatarUrl,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = profile.name, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Text(text = profile.location, style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF7B6FBF)))

            Spacer(modifier = Modifier.height(12.dp))
            StatsPill(followers = profile.followers, following = profile.following)

            Spacer(modifier = Modifier.height(12.dp))
            SocialRow(profile = profile)

            Spacer(modifier = Modifier.height(12.dp))
            TabsRow(shots = profile.shots, collections = profile.collections)

            Spacer(modifier = Modifier.height(18.dp))
            
            AsyncImage(
                model = "https://raw.githubusercontent.com/ponnamkarthik/AssetStore/main/illustrations/undraw_content_creator_re_kk0e.png",
                contentDescription = "Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun StatsPill(followers: Int, following: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF2F2F6)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$followers", fontWeight = FontWeight.Bold)
            Text(text = "Followers", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Divider(modifier = Modifier
            .height(36.dp)
            .width(1.dp)
            .background(Color(0xFFE6E6EE)))
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$following", fontWeight = FontWeight.Bold)
            Text(text = "Following", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
private fun SocialRow(profile: UserProfile) {
    val uriHandler = LocalUriHandler.current
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { if (profile.website.isNotBlank()) uriHandler.openUri(profile.website) }) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Website", tint = Color.Gray)
        }
    Spacer(modifier = Modifier.width(8.dp))
        
        Text("•", fontSize = 20.sp, color = Color(0xFF7B6FBF))
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { profile.socialProfiles.getOrNull(0)?.url?.let { uriHandler.openUri(it) } }) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Instagram", tint = Color.Gray)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text("•", fontSize = 20.sp, color = Color(0xFF7B6FBF))
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { profile.socialProfiles.getOrNull(1)?.url?.let { uriHandler.openUri(it) } }) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Facebook", tint = Color.Gray)
        }
    }
}

@Composable
private fun TabsRow(shots: Int, collections: Int) {
    var selected by remember { mutableStateOf(0) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected == 0) Color(0xFFEDE7FF) else Color(0xFFF2F2F6))
            .clickable { selected = 0 }, contentAlignment = Alignment.Center) {
            Text(text = "$shots shots", color = if (selected == 0) Color(0xFF6A3BE0) else Color.Gray, fontWeight = FontWeight.Medium)
        }
        Box(modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .padding(start = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected == 1) Color(0xFFEDE7FF) else Color(0xFFF2F2F6))
            .clickable { selected = 1 }, contentAlignment = Alignment.Center) {
            Text(text = "$collections Collections", color = if (selected == 1) Color(0xFF6A3BE0) else Color.Gray, fontWeight = FontWeight.Medium)
        }
    }
}