import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.jtkwibu.viewmodel.ProfileViewModel
import java.io.File

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val profileImagePath by viewModel.profileImagePath.collectAsState()
    val context = LocalContext.current

    var refreshTrigger by remember { mutableStateOf(0) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.setProfileImage(it)
            refreshTrigger++
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (profileImagePath != null) {
            // Menggunakan key dengan profileImagePath dan refreshTrigger
            key(profileImagePath, refreshTrigger) {
                val file = File(profileImagePath!!)
                val imageRequest = ImageRequest.Builder(context)
                    .data(file)
                    .memoryCacheKey(file.lastModified().toString())
                    .build()

                AsyncImage(
                    model = imageRequest,
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(120.dp)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Default Profile",
                modifier = Modifier.size(120.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Choose Image")
        }
    }
}