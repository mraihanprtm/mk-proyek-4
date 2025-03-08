package com.example.jabarforestwatch.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jabarforestwatch.R
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
)

val pages = listOf(
    OnboardingPage("Selamat Datang", "Jabar Forest Watch adalah tempat dimana kamu bisa mengetahui kabar mengenai lahan hutan di Jawa Barat", R.drawable.ic_launcher_foreground),
    OnboardingPage("Temukan", "Kamu bisa melihat kondisi hutan di sekitar wilayah mu", R.drawable.ic_launcher_foreground),
    OnboardingPage("Peduli Lingkungan", "Ayo jaga hutan, karena tanpanya kita tidak akan bisa menghirup udara segar!", R.drawable.ic_launcher_foreground)
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(pages[page].imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(Modifier.height(24.dp))
                Text(pages[page].title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(pages[page].description, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                OutlinedButton(onClick = { scope.launch { pagerState.scrollToPage(pagerState.currentPage - 1) } }) {
                    Text("Back")
                }
            } else {
                Spacer(Modifier.width(8.dp))
            }
            Button(onClick = {
                if (pagerState.currentPage == pages.size - 1) onFinish()
                else scope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
            }) {
                Text(if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next")
            }
        }
    }
}