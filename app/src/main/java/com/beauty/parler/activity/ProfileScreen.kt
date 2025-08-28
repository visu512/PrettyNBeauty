////////package com.beauty.parler.activity
////////
////////import androidx.compose.animation.AnimatedVisibility
////////import androidx.compose.animation.expandVertically
////////import androidx.compose.animation.fadeIn
////////import androidx.compose.animation.fadeOut
////////import androidx.compose.animation.shrinkVertically
////////import androidx.compose.foundation.Image
////////import androidx.compose.foundation.background
////////import androidx.compose.foundation.border
////////import androidx.compose.foundation.clickable
////////import androidx.compose.foundation.layout.Box
////////import androidx.compose.foundation.layout.Column
////////import androidx.compose.foundation.layout.Spacer
////////import androidx.compose.foundation.layout.fillMaxSize
////////import androidx.compose.foundation.layout.fillMaxWidth
////////import androidx.compose.foundation.layout.height
////////import androidx.compose.foundation.layout.padding
////////import androidx.compose.foundation.layout.size
////////import androidx.compose.foundation.layout.width
////////import androidx.compose.foundation.rememberScrollState
////////import androidx.compose.foundation.shape.CircleShape
////////import androidx.compose.foundation.shape.RoundedCornerShape
////////import androidx.compose.foundation.verticalScroll
////////import androidx.compose.material.icons.Icons
////////import androidx.compose.material.icons.filled.ChevronRight
////////import androidx.compose.material.icons.filled.ContactSupport
////////import androidx.compose.material.icons.filled.ExpandLess
////////import androidx.compose.material.icons.filled.Face
////////import androidx.compose.material.icons.filled.Info
////////import androidx.compose.material.icons.filled.Policy
////////import androidx.compose.material.icons.filled.Spa
////////import androidx.compose.material.icons.filled.Style
////////import androidx.compose.material3.Card
////////import androidx.compose.material3.CardDefaults
////////import androidx.compose.material3.CenterAlignedTopAppBar
////////import androidx.compose.material3.Divider
////////import androidx.compose.material3.ExperimentalMaterial3Api
////////import androidx.compose.material3.Icon
////////import androidx.compose.material3.ListItem
////////import androidx.compose.material3.MaterialTheme
////////import androidx.compose.material3.Scaffold
////////import androidx.compose.material3.Text
////////import androidx.compose.material3.TopAppBarDefaults
////////import androidx.compose.runtime.Composable
////////import androidx.compose.runtime.getValue
////////import androidx.compose.runtime.mutableStateOf
////////import androidx.compose.runtime.remember
////////import androidx.compose.runtime.setValue
////////import androidx.compose.ui.Alignment
////////import androidx.compose.ui.Modifier
////////import androidx.compose.ui.draw.clip
////////import androidx.compose.ui.graphics.Brush
////////import androidx.compose.ui.graphics.Color
////////import androidx.compose.ui.graphics.vector.ImageVector
////////import androidx.compose.ui.layout.ContentScale
////////import androidx.compose.ui.res.painterResource
////////import androidx.compose.ui.text.font.FontWeight
////////import androidx.compose.ui.unit.dp
////////import androidx.compose.ui.unit.sp
////////import androidx.navigation.NavController
////////import coil.compose.rememberAsyncImagePainter
////////import com.beauty.parler.R
////////import com.beauty.parler.ui.theme.HotPink
////////import com.beauty.parler.ui.theme.HotPinkDark
////////import com.beauty.parler.ui.theme.Pink10
////////import com.beauty.parler.ui.theme.Pink80
////////
////////@OptIn(ExperimentalMaterial3Api::class)
////////@Composable
////////fun ProfileScreen(navController: NavController) {
////////    var expandedItem by remember { mutableStateOf<String?>(null) }
////////
////////    Scaffold(
////////        topBar = {
////////            CenterAlignedTopAppBar(
////////                title = {
////////                    Text(
////////                        "My Profile",
////////                        style = MaterialTheme.typography.titleLarge,
////////                        fontWeight = FontWeight.Bold,
////////                        color = HotPinkDark
////////                    )
////////                },
////////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
////////                    containerColor = Color.White
////////                )
////////            )
////////        }
////////    ) { innerPadding ->
////////        Box(modifier = Modifier.fillMaxSize()) {
////////            Box(
////////                modifier = Modifier
////////                    .fillMaxSize()
////////                    .background(
////////                        brush = Brush.verticalGradient(
////////                            colors = listOf(Pink10, Color.White),
////////                            startY = 0f,
////////                            endY = 500f
////////                        )
////////                    )
////////            )
////////
////////            Column(
////////                modifier = Modifier
////////                    .padding(innerPadding)
////////                    .fillMaxSize()
////////                    .verticalScroll(rememberScrollState())
////////            ) {
////////                Column(
////////                    modifier = Modifier
////////                        .fillMaxWidth()
////////                        .padding(24.dp),
////////                    horizontalAlignment = Alignment.CenterHorizontally
////////                ) {
////////                    Image(
////////                        painter = rememberAsyncImagePainter(R.drawable.logo),
////////                        contentDescription = "Profile Photo",
////////                        contentScale = ContentScale.Crop,
////////                        modifier = Modifier
////////                            .size(120.dp)
////////                            .clip(CircleShape)
////////                            .border(
////////                                width = 2.dp,
////////                                color = HotPink,
////////                                shape = CircleShape
////////                            )
////////                    )
////////
////////                    Spacer(modifier = Modifier.height(16.dp))
////////
////////                    Text(
////////                        "Welcome to PrettyNBeauty",
////////                        style = MaterialTheme.typography.titleLarge,
////////                        fontWeight = FontWeight.Bold,
////////                        color = HotPinkDark
////////                    )
////////
////////                    Text(
////////                        "Book your beauty services with us",
////////                        style = MaterialTheme.typography.bodyMedium,
////////                        color = Pink80
////////                    )
////////
////////                    Spacer(modifier = Modifier.height(24.dp))
////////                }
////////
////////                // Services Section
////////                ProfileSection(title = "My Services") {
////////                    ProfileItem(
////////                        icon = Icons.Default.Spa,
////////                        title = "Hair Services",
////////                        isExpanded = expandedItem == "Hair Services",
////////                        description = "Professional hair treatments including cutting, coloring, and styling. Our expert stylists use premium products for best results.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "Hair Services") null else "Hair Services"
////////                        }
////////                    )
////////                    ProfileItem(
////////                        icon = Icons.Default.Face,
////////                        title = "Skin Care",
////////                        isExpanded = expandedItem == "Skin Care",
////////                        description = "Facial treatments and skin therapies tailored to your skin type. Achieve glowing skin with our expert estheticians.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "Skin Care") null else "Skin Care"
////////                        }
////////                    )
////////                    ProfileItem(
////////                        icon = Icons.Default.Style,
////////                        title = "Makeup",
////////                        isExpanded = expandedItem == "Makeup",
////////                        description = "Professional makeup services for all occasions. From natural looks to glamorous evening makeup, we've got you covered.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "Makeup") null else "Makeup"
////////                        }
////////                    )
////////                }
////////
////////                // Support Section
////////                ProfileSection(title = "Support") {
////////                    ProfileItem(
////////                        icon = Icons.Default.ContactSupport,
////////                        title = "Contact Us",
////////                        isExpanded = expandedItem == "Contact Us",
////////                        description = "Reach at: bookingprettynbeauty@gmail.com. Our team is available 8AM-6PM daily.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "Contact Us") null else "Contact Us"
////////                        }
////////                    )
////////                    ProfileItem(
////////                        icon = Icons.Default.Info,
////////                        title = "About Us",
////////                        isExpanded = expandedItem == "About Us",
////////                        description = "PrettyNBeauty has been providing premium beauty services since 2022. We believe in enhancing natural beauty with expert care.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "About Us") null else "About Us"
////////                        }
////////                    )
////////                    ProfileItem(
////////                        icon = Icons.Default.Policy,
////////                        title = "Privacy Policy",
////////                        isExpanded = expandedItem == "Privacy Policy",
////////                        description = "We value your privacy. Your personal information is securely stored and never shared with third parties without consent.",
////////                        onClick = {
////////                            expandedItem = if (expandedItem == "Privacy Policy") null else "Privacy Policy"
////////                        }
////////                    )
////////                }
////////
////////                Spacer(modifier = Modifier.height(24.dp))
////////            }
////////        }
////////    }
////////}
////////
////////@Composable
////////fun ProfileSection(title: String, content: @Composable () -> Unit) {
////////    Card(
////////        modifier = Modifier
////////            .padding(horizontal = 16.dp, vertical = 8.dp)
////////            .fillMaxWidth(),
////////        shape = RoundedCornerShape(20.dp),
////////        colors = CardDefaults.cardColors(
////////            containerColor = Color.White
////////        ),
////////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
////////    ) {
////////        Column {
////////            Text(
////////                text = title,
////////                style = MaterialTheme.typography.titleSmall,
////////                fontWeight = FontWeight.Bold,
////////                color = HotPinkDark,
////////                modifier = Modifier.padding(16.dp)
////////            )
////////
////////            content()
////////        }
////////    }
////////}
////////
////////@Composable
////////fun ProfileItem(
////////    icon: ImageVector,
////////    title: String,
////////    isExpanded: Boolean,
////////    description: String,
////////    onClick: () -> Unit
////////) {
////////    Column {
////////        ListItem(
////////            modifier = Modifier.clickable { onClick() },
////////            leadingContent = {
////////                Icon(
////////                    imageVector = icon,
////////                    contentDescription = title,
////////                    tint = HotPink
////////                )
////////            },
////////            headlineContent = {
////////                Text(
////////                    text = title,
////////                    style = MaterialTheme.typography.bodyMedium,
////////                    color = HotPinkDark
////////                )
////////            },
////////            trailingContent = {
////////                Icon(
////////                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
////////                    contentDescription = if (isExpanded) "Collapse" else "Expand",
////////                    tint = Pink80
////////                )
////////            }
////////        )
////////
////////        AnimatedVisibility(
////////            visible = isExpanded,
////////            enter = fadeIn() + expandVertically(),
////////            exit = fadeOut() + shrinkVertically()
////////        ) {
////////            Text(
////////                text = description,
////////                style = MaterialTheme.typography.bodySmall,
////////                color = Color.Gray,
////////                modifier = Modifier.padding(
////////                    start = 72.dp,
////////                    end = 16.dp,
////////                    bottom = 16.dp
////////                )
////////            )
////////        }
////////
////////        Divider(
////////            modifier = Modifier.padding(horizontal = 16.dp),
////////            thickness = 0.5.dp,
////////            color = Pink10
////////        )
////////    }
////////}
////////
////////
////////
////////
////////
////////
////////
////////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////package com.beauty.parler.activity
//////
//////import android.content.Intent
//////import androidx.compose.animation.AnimatedVisibility
//////import androidx.compose.animation.expandVertically
//////import androidx.compose.animation.fadeIn
//////import androidx.compose.animation.fadeOut
//////import androidx.compose.animation.shrinkVertically
//////import androidx.compose.foundation.Image
//////import androidx.compose.foundation.background
//////import androidx.compose.foundation.border
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.Arrangement
//////import androidx.compose.foundation.layout.Box
//////import androidx.compose.foundation.layout.Column
//////import androidx.compose.foundation.layout.Row
//////import androidx.compose.foundation.layout.Spacer
//////import androidx.compose.foundation.layout.fillMaxSize
//////import androidx.compose.foundation.layout.fillMaxWidth
//////import androidx.compose.foundation.layout.height
//////import androidx.compose.foundation.layout.padding
//////import androidx.compose.foundation.layout.size
//////import androidx.compose.foundation.layout.width
//////import androidx.compose.foundation.rememberScrollState
//////import androidx.compose.foundation.shape.CircleShape
//////import androidx.compose.foundation.shape.RoundedCornerShape
//////import androidx.compose.foundation.text.KeyboardOptions
//////import androidx.compose.foundation.verticalScroll
//////import androidx.compose.material.icons.Icons
//////import androidx.compose.material.icons.filled.ChevronRight
//////import androidx.compose.material.icons.filled.ContactSupport
//////import androidx.compose.material.icons.filled.ExpandLess
//////import androidx.compose.material.icons.filled.Face
//////import androidx.compose.material.icons.filled.Info
//////import androidx.compose.material.icons.filled.Logout
//////import androidx.compose.material.icons.filled.Policy
//////import androidx.compose.material.icons.filled.Spa
//////import androidx.compose.material.icons.filled.Style
//////import androidx.compose.material3.Button
//////import androidx.compose.material3.ButtonDefaults
//////import androidx.compose.material3.Card
//////import androidx.compose.material3.CardDefaults
//////import androidx.compose.material3.CenterAlignedTopAppBar
//////import androidx.compose.material3.CircularProgressIndicator
//////import androidx.compose.material3.Divider
//////import androidx.compose.material3.ExperimentalMaterial3Api
//////import androidx.compose.material3.Icon
//////import androidx.compose.material3.IconButton
//////import androidx.compose.material3.ListItem
//////import androidx.compose.material3.MaterialTheme
//////import androidx.compose.material3.OutlinedTextField
//////import androidx.compose.material3.Scaffold
//////import androidx.compose.material3.Text
//////import androidx.compose.material3.TextButton
//////import androidx.compose.material3.TopAppBarDefaults
//////import androidx.compose.runtime.Composable
//////import androidx.compose.runtime.getValue
//////import androidx.compose.runtime.mutableStateOf
//////import androidx.compose.runtime.remember
//////import androidx.compose.runtime.setValue
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.clip
//////import androidx.compose.ui.graphics.Brush
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.graphics.vector.ImageVector
//////import androidx.compose.ui.layout.ContentScale
//////import androidx.compose.ui.platform.LocalContext
//////import androidx.compose.ui.res.painterResource
//////import androidx.compose.ui.text.font.FontWeight
//////import androidx.compose.ui.text.input.KeyboardType
//////import androidx.compose.ui.unit.dp
//////import androidx.compose.ui.unit.sp
//////import androidx.compose.ui.window.Dialog
//////import androidx.navigation.NavController
//////import coil.compose.rememberAsyncImagePainter
//////import com.beauty.parler.R
//////import com.beauty.parler.ui.theme.HotPink
//////import com.beauty.parler.ui.theme.HotPinkDark
//////import com.beauty.parler.ui.theme.Pink10
//////import com.beauty.parler.ui.theme.Pink80
//////import com.google.firebase.FirebaseException
//////import com.google.firebase.auth.FirebaseAuth
//////import com.google.firebase.auth.PhoneAuthCredential
//////import com.google.firebase.auth.PhoneAuthOptions
//////import com.google.firebase.auth.PhoneAuthProvider
//////import com.google.firebase.auth.ktx.auth
//////import com.google.firebase.ktx.Firebase
//////import java.util.concurrent.TimeUnit
//////
//////@OptIn(ExperimentalMaterial3Api::class)
//////@Composable
//////fun ProfileScreen(navController: NavController) {
//////    var expandedItem by remember { mutableStateOf<String?>(null) }
//////    val auth = Firebase.auth
//////    val currentUser = auth.currentUser
//////    val context = LocalContext.current
//////    var showLoginDialog by remember { mutableStateOf(false) }
//////
//////    Scaffold(
//////        topBar = {
//////            CenterAlignedTopAppBar(
//////                title = {
//////                    Text(
//////                        "My Profile",
//////                        style = MaterialTheme.typography.titleLarge,
//////                        fontWeight = FontWeight.Bold,
//////                        color = HotPinkDark
//////                    )
//////                },
//////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//////                    containerColor = Color.White
//////                ),
//////                actions = {
//////                    if (currentUser == null) {
//////                        Text(
//////                            "Login",
//////                            color = HotPink,
//////                            modifier = Modifier
//////                                .padding(end = 16.dp)
//////                                .clickable { showLoginDialog = true }
//////                        )
//////                    } else {
//////                        IconButton(
//////                            onClick = {
//////                                Firebase.auth.signOut()
//////                                val intent = Intent(context, SplashActivity::class.java)
//////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//////                                context.startActivity(intent)
//////                            }
//////                        ) {
//////                            Icon(
//////                                imageVector = Icons.Default.Logout,
//////                                contentDescription = "Logout",
//////                                tint = HotPink
//////                            )
//////                        }
//////                    }
//////                }
//////            )
//////        }
//////    ) { innerPadding ->
//////        Box(modifier = Modifier.fillMaxSize()) {
//////            Box(
//////                modifier = Modifier
//////                    .fillMaxSize()
//////                    .background(
//////                        brush = Brush.verticalGradient(
//////                            colors = listOf(Pink10, Color.White),
//////                            startY = 0f,
//////                            endY = 500f
//////                        )
//////                    )
//////            )
//////
//////            Column(
//////                modifier = Modifier
//////                    .padding(innerPadding)
//////                    .fillMaxSize()
//////                    .verticalScroll(rememberScrollState())
//////            ) {
//////                if (currentUser != null) {
//////                    Column(
//////                        modifier = Modifier
//////                            .fillMaxWidth()
//////                            .padding(24.dp),
//////                        horizontalAlignment = Alignment.CenterHorizontally
//////                    ) {
//////                        Image(
//////                            painter = rememberAsyncImagePainter(R.drawable.logo),
//////                            contentDescription = "Profile Photo",
//////                            contentScale = ContentScale.Crop,
//////                            modifier = Modifier
//////                                .size(120.dp)
//////                                .clip(CircleShape)
//////                                .border(
//////                                    width = 2.dp,
//////                                    color = HotPink,
//////                                    shape = CircleShape
//////                                )
//////                        )
//////
//////                        Spacer(modifier = Modifier.height(16.dp))
//////
//////                        Text(
//////                            "Welcome to PrettyNBeauty",
//////                            style = MaterialTheme.typography.titleLarge,
//////                            fontWeight = FontWeight.Bold,
//////                            color = HotPinkDark
//////                        )
//////
//////                        // Display phone number if available
//////                        currentUser.phoneNumber?.let { phone ->
//////                            Spacer(modifier = Modifier.height(4.dp))
//////                            Text(
//////                                phone,
//////                                style = MaterialTheme.typography.bodyMedium,
//////                                color = Pink80
//////                            )
//////                        } ?: run {
//////                            Text(
//////                                "Book your beauty services with us",
//////                                style = MaterialTheme.typography.bodyMedium,
//////                                color = Pink80
//////                            )
//////                        }
//////
//////                        Spacer(modifier = Modifier.height(24.dp))
//////                    }
//////
//////                    // Services Section
//////                    ProfileSection(title = "My Services") {
//////                        ProfileItem(
//////                            icon = Icons.Default.Spa,
//////                            title = "Hair Services",
//////                            isExpanded = expandedItem == "Hair Services",
//////                            description = "Professional hair treatments including cutting, coloring, and styling. Our expert stylists use premium products for best results.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "Hair Services") null else "Hair Services"
//////                            }
//////                        )
//////                        ProfileItem(
//////                            icon = Icons.Default.Face,
//////                            title = "Skin Care",
//////                            isExpanded = expandedItem == "Skin Care",
//////                            description = "Facial treatments and skin therapies tailored to your skin type. Achieve glowing skin with our expert estheticians.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "Skin Care") null else "Skin Care"
//////                            }
//////                        )
//////                        ProfileItem(
//////                            icon = Icons.Default.Style,
//////                            title = "Makeup",
//////                            isExpanded = expandedItem == "Makeup",
//////                            description = "Professional makeup services for all occasions. From natural looks to glamorous evening makeup, we've got you covered.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "Makeup") null else "Makeup"
//////                            }
//////                        )
//////                    }
//////
//////                    // Support Section
//////                    ProfileSection(title = "Support") {
//////                        ProfileItem(
//////                            icon = Icons.Default.ContactSupport,
//////                            title = "Contact Us",
//////                            isExpanded = expandedItem == "Contact Us",
//////                            description = "Reach at: bookingprettynbeauty@gmail.com. Our team is available 8AM-6PM daily.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "Contact Us") null else "Contact Us"
//////                            }
//////                        )
//////                        ProfileItem(
//////                            icon = Icons.Default.Info,
//////                            title = "About Us",
//////                            isExpanded = expandedItem == "About Us",
//////                            description = "PrettyNBeauty has been providing premium beauty services since 2022. We believe in enhancing natural beauty with expert care.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "About Us") null else "About Us"
//////                            }
//////                        )
//////                        ProfileItem(
//////                            icon = Icons.Default.Policy,
//////                            title = "Privacy Policy",
//////                            isExpanded = expandedItem == "Privacy Policy",
//////                            description = "We value your privacy. Your personal information is securely stored and never shared with third parties without consent.",
//////                            onClick = {
//////                                expandedItem = if (expandedItem == "Privacy Policy") null else "Privacy Policy"
//////                            }
//////                        )
//////                    }
//////
//////                    // Logout Section
//////                    ProfileSection(title = "Account") {
//////                        ProfileItem(
//////                            icon = Icons.Default.Logout,
//////                            title = "Logout",
//////                            isExpanded = expandedItem == "Logout",
//////                            description = "Sign out from your account. You'll need to login again to access your profile.",
//////                            onClick = {
//////                                Firebase.auth.signOut()
//////                                val intent = Intent(context, SplashActivity::class.java)
//////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//////                                context.startActivity(intent)
//////                            }
//////                        )
//////                    }
//////                } else {
//////                    // Show welcome message for non-logged in users
//////                    Column(
//////                        modifier = Modifier
//////                            .fillMaxWidth()
//////                            .padding(24.dp),
//////                        horizontalAlignment = Alignment.CenterHorizontally
//////                    ) {
//////                        Image(
//////                            painter = painterResource(R.drawable.logo),
//////                            contentDescription = "App Logo",
//////                            contentScale = ContentScale.Crop,
//////                            modifier = Modifier
//////                                .size(120.dp)
//////                                .clip(CircleShape)
//////                        )
//////
//////                        Spacer(modifier = Modifier.height(16.dp))
//////
//////                        Text(
//////                            "Welcome to PrettyNBeauty",
//////                            style = MaterialTheme.typography.titleLarge,
//////                            fontWeight = FontWeight.Bold,
//////                            color = HotPinkDark
//////                        )
//////
//////                        Spacer(modifier = Modifier.height(8.dp))
//////
//////                        Text(
//////                            "Please login to access your profile and book services",
//////                            style = MaterialTheme.typography.bodyMedium,
//////                            color = Pink80,
//////                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
//////                        )
//////
//////                        Spacer(modifier = Modifier.height(24.dp))
//////
//////                        Button(
//////                            onClick = { showLoginDialog = true },
//////                            colors = ButtonDefaults.buttonColors(
//////                                containerColor = HotPink
//////                            )
//////                        ) {
//////                            Text("Login with Phone Number")
//////                        }
//////                    }
//////                }
//////
//////                Spacer(modifier = Modifier.height(24.dp))
//////            }
//////        }
//////    }
//////
//////    // Phone Login Dialog
//////    if (showLoginDialog) {
//////        PhoneLoginDialog(
//////            onDismiss = { showLoginDialog = false },
//////            onVerificationSuccess = {
//////                showLoginDialog = false
//////                // Refresh the screen to show user profile
//////            }
//////        )
//////    }
//////}
//////
//////@Composable
//////fun PhoneLoginDialog(onDismiss: () -> Unit, onVerificationSuccess: () -> Unit) {
//////    var phoneNumber by remember { mutableStateOf("") }
//////    var verificationCode by remember { mutableStateOf("") }
//////    var verificationId by remember { mutableStateOf<String?>(null) }
//////    var isLoading by remember { mutableStateOf(false) }
//////    var errorMessage by remember { mutableStateOf<String?>(null) }
//////    var successMessage by remember { mutableStateOf<String?>(null) }
//////    val auth = Firebase.auth
//////    val context = LocalContext.current
//////
//////    Dialog(onDismissRequest = onDismiss) {
//////        Card(
//////            modifier = Modifier
//////                .fillMaxWidth()
//////                .padding(16.dp),
//////            shape = RoundedCornerShape(16.dp),
//////            colors = CardDefaults.cardColors(
//////                containerColor = Color.White
//////            )
//////        ) {
//////            Column(
//////                modifier = Modifier
//////                    .padding(24.dp)
//////            ) {
//////                Text(
//////                    "Login with Phone Number",
//////                    style = MaterialTheme.typography.titleLarge,
//////                    fontWeight = FontWeight.Bold,
//////                    color = HotPinkDark
//////                )
//////
//////                Spacer(modifier = Modifier.height(16.dp))
//////
//////                if (successMessage != null) {
//////                    Text(
//////                        successMessage!!,
//////                        color = HotPinkDark,
//////                        modifier = Modifier.padding(vertical = 8.dp)
//////                    )
//////                    Spacer(modifier = Modifier.height(16.dp))
//////                }
//////
//////                if (verificationId == null) {
//////                    // Phone number input
//////                    OutlinedTextField(
//////                        value = phoneNumber,
//////                        onValueChange = { phoneNumber = it },
//////                        label = { Text("Phone Number") },
//////                        placeholder = { Text("Enter 10-digit number") },
//////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//////                        modifier = Modifier.fillMaxWidth(),
//////                        isError = errorMessage != null
//////                    )
//////
//////                    Text(
//////                        "OTP will be sent to +91$phoneNumber",
//////                        color = Pink80,
//////                        fontSize = 12.sp,
//////                        modifier = Modifier.padding(top = 4.dp)
//////                    )
//////
//////                    if (errorMessage != null) {
//////                        Text(
//////                            errorMessage!!,
//////                            color = Color.Red,
//////                            modifier = Modifier.padding(top = 8.dp)
//////                        )
//////                    }
//////
//////                    Spacer(modifier = Modifier.height(16.dp))
//////
//////                    Button(
//////                        onClick = {
//////                            if (phoneNumber.length == 10) {
//////                                isLoading = true
//////                                errorMessage = null
//////                                // Simulate OTP sending (replace with actual Firebase Auth)
//////                                verificationId = "simulated_verification_id"
//////                                isLoading = false
//////                                successMessage = "OTP sent to +91$phoneNumber"
//////                            } else {
//////                                errorMessage = "Please enter a valid 10-digit phone number"
//////                            }
//////                        },
//////                        modifier = Modifier.fillMaxWidth(),
//////                        enabled = !isLoading,
//////                        colors = ButtonDefaults.buttonColors(
//////                            containerColor = HotPink
//////                        )
//////                    ) {
//////                        if (isLoading) {
//////                            CircularProgressIndicator(
//////                                modifier = Modifier.size(20.dp),
//////                                color = Color.White,
//////                                strokeWidth = 2.dp
//////                            )
//////                        } else {
//////                            Text("Send OTP")
//////                        }
//////                    }
//////                } else {
//////                    // OTP input
//////                    OutlinedTextField(
//////                        value = verificationCode,
//////                        onValueChange = { verificationCode = it },
//////                        label = { Text("Verification Code") },
//////                        placeholder = { Text("Enter 6-digit code") },
//////                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//////                        modifier = Modifier.fillMaxWidth(),
//////                        isError = errorMessage != null
//////                    )
//////
//////                    if (errorMessage != null) {
//////                        Text(
//////                            errorMessage!!,
//////                            color = Color.Red,
//////                            modifier = Modifier.padding(top = 8.dp)
//////                        )
//////                    }
//////
//////                    Spacer(modifier = Modifier.height(16.dp))
//////
//////                    Row(
//////                        modifier = Modifier.fillMaxWidth(),
//////                        horizontalArrangement = Arrangement.SpaceBetween
//////                    ) {
//////                        TextButton(
//////                            onClick = {
//////                                verificationId = null
//////                                successMessage = null
//////                            }
//////                        ) {
//////                            Text("Change Number")
//////                        }
//////
//////                        Button(
//////                            onClick = {
//////                                if (verificationCode.length == 6) {
//////                                    isLoading = true
//////                                    // Simulate OTP verification (replace with actual Firebase Auth)
//////                                    if (verificationCode == "123456") { // Simulated OTP
//////                                        onVerificationSuccess()
//////                                        successMessage = "Login successful!"
//////                                    } else {
//////                                        errorMessage = "Invalid OTP. Please try again."
//////                                    }
//////                                    isLoading = false
//////                                } else {
//////                                    errorMessage = "Please enter a valid 6-digit verification code"
//////                                }
//////                            },
//////                            enabled = !isLoading,
//////                            colors = ButtonDefaults.buttonColors(
//////                                containerColor = HotPink
//////                            )
//////                        ) {
//////                            if (isLoading) {
//////                                CircularProgressIndicator(
//////                                    modifier = Modifier.size(20.dp),
//////                                    color = Color.White,
//////                                    strokeWidth = 2.dp
//////                                )
//////                            } else {
//////                                Text("Verify OTP")
//////                            }
//////                        }
//////                    }
//////                }
//////
//////                Spacer(modifier = Modifier.height(8.dp))
//////
//////                TextButton(
//////                    onClick = onDismiss,
//////                    modifier = Modifier.fillMaxWidth()
//////                ) {
//////                    Text("Cancel")
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun ProfileSection(title: String, content: @Composable () -> Unit) {
//////    Card(
//////        modifier = Modifier
//////            .padding(horizontal = 16.dp, vertical = 8.dp)
//////            .fillMaxWidth(),
//////        shape = RoundedCornerShape(20.dp),
//////        colors = CardDefaults.cardColors(
//////            containerColor = Color.White
//////        ),
//////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//////    ) {
//////        Column {
//////            Text(
//////                text = title,
//////                style = MaterialTheme.typography.titleSmall,
//////                fontWeight = FontWeight.Bold,
//////                color = HotPinkDark,
//////                modifier = Modifier.padding(16.dp)
//////            )
//////
//////            content()
//////        }
//////    }
//////}
//////
//////@Composable
//////fun ProfileItem(
//////    icon: ImageVector,
//////    title: String,
//////    isExpanded: Boolean,
//////    description: String,
//////    onClick: () -> Unit
//////) {
//////    Column {
//////        ListItem(
//////            modifier = Modifier.clickable { onClick() },
//////            leadingContent = {
//////                Icon(
//////                    imageVector = icon,
//////                    contentDescription = title,
//////                    tint = HotPink
//////                )
//////            },
//////            headlineContent = {
//////                Text(
//////                    text = title,
//////                    style = MaterialTheme.typography.bodyMedium,
//////                    color = HotPinkDark
//////                )
//////            },
//////            trailingContent = {
//////                Icon(
//////                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
//////                    contentDescription = if (isExpanded) "Collapse" else "Expand",
//////                    tint = Pink80
//////                )
//////            }
//////        )
//////
//////        AnimatedVisibility(
//////            visible = isExpanded,
//////            enter = fadeIn() + expandVertically(),
//////            exit = fadeOut() + shrinkVertically()
//////        ) {
//////            Text(
//////                text = description,
//////                style = MaterialTheme.typography.bodySmall,
//////                color = Color.Gray,
//////                modifier = Modifier.padding(
//////                    start = 72.dp,
//////                    end = 16.dp,
//////                    bottom = 16.dp
//////                )
//////            )
//////        }
//////
//////        Divider(
//////            modifier = Modifier.padding(horizontal = 16.dp),
//////            thickness = 0.5.dp,
//////            color = Pink10
//////        )
//////    }
//////}
////
////
////
////
////
////
////
////
////
////
////
////
////package com.beauty.parler.activity
////
////import androidx.compose.animation.AnimatedVisibility
////import androidx.compose.animation.expandVertically
////import androidx.compose.animation.fadeIn
////import androidx.compose.animation.fadeOut
////import androidx.compose.animation.shrinkVertically
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.background
////import androidx.compose.foundation.border
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.Box
////import androidx.compose.foundation.layout.Column
////import androidx.compose.foundation.layout.Spacer
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.fillMaxWidth
////import androidx.compose.foundation.layout.height
////import androidx.compose.foundation.layout.padding
////import androidx.compose.foundation.layout.size
////import androidx.compose.foundation.layout.width
////import androidx.compose.foundation.rememberScrollState
////import androidx.compose.foundation.shape.CircleShape
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.foundation.verticalScroll
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.ChevronRight
////import androidx.compose.material.icons.filled.ContactSupport
////import androidx.compose.material.icons.filled.ExpandLess
////import androidx.compose.material.icons.filled.Face
////import androidx.compose.material.icons.filled.Info
////import androidx.compose.material.icons.filled.Policy
////import androidx.compose.material.icons.filled.Spa
////import androidx.compose.material.icons.filled.Style
////import androidx.compose.material3.Button
////import androidx.compose.material3.ButtonDefaults
////import androidx.compose.material3.Card
////import androidx.compose.material3.CardDefaults
////import androidx.compose.material3.CenterAlignedTopAppBar
////import androidx.compose.material3.Divider
////import androidx.compose.material3.ExperimentalMaterial3Api
////import androidx.compose.material3.Icon
////import androidx.compose.material3.ListItem
////import androidx.compose.material3.MaterialTheme
////import androidx.compose.material3.Scaffold
////import androidx.compose.material3.Text
////import androidx.compose.material3.TopAppBarDefaults
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.getValue
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.remember
////import androidx.compose.runtime.setValue
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Brush
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.graphics.vector.ImageVector
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.res.painterResource
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.navigation.NavController
////import coil.compose.rememberAsyncImagePainter
////import com.beauty.parler.R
////import com.beauty.parler.ui.theme.HotPink
////import com.beauty.parler.ui.theme.HotPinkDark
////import com.beauty.parler.ui.theme.Pink10
////import com.beauty.parler.ui.theme.Pink80
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun ProfileScreen(navController: NavController) {
////    var expandedItem by remember { mutableStateOf<String?>(null) }
////    var isLoggedIn by remember { mutableStateOf(false) }
////    var showLoginDialog by remember { mutableStateOf(false) }
////    var userPhoneNumber by remember { mutableStateOf("") }
////
////    if (showLoginDialog) {
////        PhoneLoginDialog(
////            onDismiss = { showLoginDialog = false },
////            onLoginSuccess = { phoneNumber ->
////                isLoggedIn = true
////                userPhoneNumber = phoneNumber
////                showLoginDialog = false
////            }
////        )
////    }
////
////    Scaffold(
////        topBar = {
////            CenterAlignedTopAppBar(
////                title = {
////                    Text(
////                        "My Profile",
////                        style = MaterialTheme.typography.titleLarge,
////                        fontWeight = FontWeight.Bold,
////                        color = HotPinkDark
////                    )
////                },
////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
////                    containerColor = Color.White
////                ),
////                actions = {
////                    if (!isLoggedIn) {
////                        Text(
////                            "Login",
////                            modifier = Modifier
////                                .padding(end = 16.dp)
////                                .clickable { showLoginDialog = true },
////                            color = HotPink,
////                            fontWeight = FontWeight.Medium
////                        )
////                    }
////                }
////            )
////        }
////    ) { innerPadding ->
////        Box(modifier = Modifier.fillMaxSize()) {
////            Box(
////                modifier = Modifier
////                    .fillMaxSize()
////                    .background(
////                        brush = Brush.verticalGradient(
////                            colors = listOf(Pink10, Color.White),
////                            startY = 0f,
////                            endY = 500f
////                        )
////                    )
////            )
////
////            Column(
////                modifier = Modifier
////                    .padding(innerPadding)
////                    .fillMaxSize()
////                    .verticalScroll(rememberScrollState())
////            ) {
////                Column(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(24.dp),
////                    horizontalAlignment = Alignment.CenterHorizontally
////                ) {
////                    Image(
////                        painter = rememberAsyncImagePainter(R.drawable.logo),
////                        contentDescription = "Profile Photo",
////                        contentScale = ContentScale.Crop,
////                        modifier = Modifier
////                            .size(120.dp)
////                            .clip(CircleShape)
////                            .border(
////                                width = 2.dp,
////                                color = HotPink,
////                                shape = CircleShape
////                            )
////                    )
////
////                    Spacer(modifier = Modifier.height(16.dp))
////
////                    Text(
////                        "Welcome to PrettyNBeauty",
////                        style = MaterialTheme.typography.titleLarge,
////                        fontWeight = FontWeight.Bold,
////                        color = HotPinkDark
////                    )
////
////                    Text(
////                        "Book your beauty services with us",
////                        style = MaterialTheme.typography.bodyMedium,
////                        color = Pink80
////                    )
////
////                    if (isLoggedIn && userPhoneNumber.isNotEmpty()) {
////                        Spacer(modifier = Modifier.height(8.dp))
////                        Text(
////                            userPhoneNumber,
////                            style = MaterialTheme.typography.bodyMedium,
////                            color = HotPink,
////                            fontWeight = FontWeight.Medium
////                        )
////                    }
////
////                    Spacer(modifier = Modifier.height(24.dp))
////                }
////
////                // Services Section
////                ProfileSection(title = "My Services") {
////                    ProfileItem(
////                        icon = Icons.Default.Spa,
////                        title = "Hair Services",
////                        isExpanded = expandedItem == "Hair Services",
////                        description = "Professional hair treatments including cutting, coloring, and styling. Our expert stylists use premium products for best results.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "Hair Services") null else "Hair Services"
////                        }
////                    )
////                    ProfileItem(
////                        icon = Icons.Default.Face,
////                        title = "Skin Care",
////                        isExpanded = expandedItem == "Skin Care",
////                        description = "Facial treatments and skin therapies tailored to your skin type. Achieve glowing skin with our expert estheticians.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "Skin Care") null else "Skin Care"
////                        }
////                    )
////                    ProfileItem(
////                        icon = Icons.Default.Style,
////                        title = "Makeup",
////                        isExpanded = expandedItem == "Makeup",
////                        description = "Professional makeup services for all occasions. From natural looks to glamorous evening makeup, we've got you covered.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "Makeup") null else "Makeup"
////                        }
////                    )
////                }
////
////                // Support Section
////                ProfileSection(title = "Support") {
////                    ProfileItem(
////                        icon = Icons.Default.ContactSupport,
////                        title = "Contact Us",
////                        isExpanded = expandedItem == "Contact Us",
////                        description = "Reach at: bookingprettynbeauty@gmail.com. Our team is available 8AM-6PM daily.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "Contact Us") null else "Contact Us"
////                        }
////                    )
////                    ProfileItem(
////                        icon = Icons.Default.Info,
////                        title = "About Us",
////                        isExpanded = expandedItem == "About Us",
////                        description = "PrettyNBeauty has been providing premium beauty services since 2022. We believe in enhancing natural beauty with expert care.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "About Us") null else "About Us"
////                        }
////                    )
////                    ProfileItem(
////                        icon = Icons.Default.Policy,
////                        title = "Privacy Policy",
////                        isExpanded = expandedItem == "Privacy Policy",
////                        description = "We value your privacy. Your personal information is securely stored and never shared with third parties without consent.",
////                        onClick = {
////                            expandedItem = if (expandedItem == "Privacy Policy") null else "Privacy Policy"
////                        }
////                    )
////
////                    // Logout Button
////                    if (isLoggedIn) {
////                        Button(
////                            onClick = {
////                                isLoggedIn = false
////                                userPhoneNumber = ""
////                                // Navigate to splash activity
////                                navController.navigate("splash") {
////                                    popUpTo(navController.graph.startDestinationId) {
////                                        inclusive = true
////                                    }
////                                }
////                            },
////                            modifier = Modifier
////                                .fillMaxWidth()
////                                .padding(16.dp),
////                            shape = RoundedCornerShape(12.dp)
////                        ) {
////                            Text("Logout", modifier = Modifier.padding(vertical = 8.dp))
////                        }
////                    }
////                }
////
////                Spacer(modifier = Modifier.height(24.dp))
////            }
////        }
////    }
////}
////
////@Composable
////fun PhoneLoginDialog(
////    onDismiss: () -> Unit,
////    onLoginSuccess: (String) -> Unit
////) {
////    var phoneNumber by remember { mutableStateOf("") }
////    var otp by remember { mutableStateOf("") }
////    var showOtpField by remember { mutableStateOf(false) }
////
////    // This would be a custom dialog implementation
////    // For simplicity, I'm showing a basic outline
////    Box(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(Color.Black.copy(alpha = 0.5f))
////            .clickable(onClick = onDismiss),
////        contentAlignment = Alignment.Center
////    ) {
////        Card(
////            modifier = Modifier
////                .width(300.dp)
////                .padding(16.dp),
////            shape = RoundedCornerShape(16.dp)
////        ) {
////            Column(
////                modifier = Modifier.padding(16.dp)
////            ) {
////                Text(
////                    "Login with Phone",
////                    style = MaterialTheme.typography.titleMedium,
////                    fontWeight = FontWeight.Bold,
////                    color = HotPinkDark
////                )
////
////                Spacer(modifier = Modifier.height(16.dp))
////
////                if (!showOtpField) {
////                    // Phone number input
////                    Text("Enter your phone number")
////                    // Add phone input field here
////                    // For simplicity, using a placeholder
////
////                    Button(
////                        onClick = {
////                            // Validate phone number
////                            // Send OTP
////                            showOtpField = true
////                        },
////                        modifier = Modifier.fillMaxWidth()
////                    ) {
////                        Text("Send OTP")
////                    }
////                } else {
////                    // OTP input
////                    Text("Enter OTP sent to $phoneNumber")
////                    // Add OTP input field here
////                    // For simplicity, using a placeholder
////
////                    Button(
////                        onClick = {
////                            // Verify OTP
////                            onLoginSuccess(phoneNumber)
////                        },
////                        modifier = Modifier.fillMaxWidth()
////                    ) {
////                        Text("Verify & Login")
////                    }
////                }
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                Button(
////                    onClick = onDismiss,
////                    modifier = Modifier.fillMaxWidth(),
////                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
////                ) {
////                    Text("Cancel")
////                }
////            }
////        }
////    }
////}
////
////@Composable
////fun ProfileSection(title: String, content: @Composable () -> Unit) {
////    Card(
////        modifier = Modifier
////            .padding(horizontal = 16.dp, vertical = 8.dp)
////            .fillMaxWidth(),
////        shape = RoundedCornerShape(20.dp),
////        colors = CardDefaults.cardColors(
////            containerColor = Color.White
////        ),
////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
////    ) {
////        Column {
////            Text(
////                text = title,
////                style = MaterialTheme.typography.titleSmall,
////                fontWeight = FontWeight.Bold,
////                color = HotPinkDark,
////                modifier = Modifier.padding(16.dp)
////            )
////
////            content()
////        }
////    }
////}
////
////@Composable
////fun ProfileItem(
////    icon: ImageVector,
////    title: String,
////    isExpanded: Boolean,
////    description: String,
////    onClick: () -> Unit
////) {
////    Column {
////        ListItem(
////            modifier = Modifier.clickable { onClick() },
////            leadingContent = {
////                Icon(
////                    imageVector = icon,
////                    contentDescription = title,
////                    tint = HotPink
////                )
////            },
////            headlineContent = {
////                Text(
////                    text = title,
////                    style = MaterialTheme.typography.bodyMedium,
////                    color = HotPinkDark
////                )
////            },
////            trailingContent = {
////                Icon(
////                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
////                    contentDescription = if (isExpanded) "Collapse" else "Expand",
////                    tint = Pink80
////                )
////            }
////        )
////
////        AnimatedVisibility(
////            visible = isExpanded,
////            enter = fadeIn() + expandVertically(),
////            exit = fadeOut() + shrinkVertically()
////        ) {
////            Text(
////                text = description,
////                style = MaterialTheme.typography.bodySmall,
////                color = Color.Gray,
////                modifier = Modifier.padding(
////                    start = 72.dp,
////                    end = 16.dp,
////                    bottom = 16.dp
////                )
////            )
////        }
////
////        Divider(
////            modifier = Modifier.padding(horizontal = 16.dp),
////            thickness = 0.5.dp,
////            color = Pink10
////        )
////    }
////}
//
//
//
//
//
//
//
//
//
//
//
//package com.beauty.parler.activity
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.expandVertically
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.shrinkVertically
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ChevronRight
//import androidx.compose.material.icons.filled.ContactSupport
//import androidx.compose.material.icons.filled.ExpandLess
//import androidx.compose.material.icons.filled.Face
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.Policy
//import androidx.compose.material.icons.filled.Spa
//import androidx.compose.material.icons.filled.Style
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.navigation.NavController
//import coil.compose.rememberAsyncImagePainter
//import com.beauty.parler.R
//import com.beauty.parler.ui.theme.HotPink
//import com.beauty.parler.ui.theme.HotPinkDark
//import com.beauty.parler.ui.theme.Pink10
//import com.beauty.parler.ui.theme.Pink80
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileScreen(navController: NavController) {
//    var expandedItem by remember { mutableStateOf<String?>(null) }
//    var isLoggedIn by remember { mutableStateOf(false) }
//    var showLoginDialog by remember { mutableStateOf(false) }
//    var userPhoneNumber by remember { mutableStateOf("") }
//
//    if (showLoginDialog) {
//        PhoneLoginDialog(
//            onDismiss = { showLoginDialog = false },
//            onLoginSuccess = { phoneNumber ->
//                isLoggedIn = true
//                userPhoneNumber = phoneNumber
//                showLoginDialog = false
//            }
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "My Profile",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.White
//                ),
//                actions = {
//                    if (!isLoggedIn) {
//                        Text(
//                            "Login",
//                            modifier = Modifier
//                                .padding(end = 16.dp)
//                                .clickable { showLoginDialog = true },
//                            color = HotPink,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.fillMaxSize()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(Pink10, Color.White),
//                            startY = 0f,
//                            endY = 500f
//                        )
//                    )
//            )
//
//            Column(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(24.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        painter = rememberAsyncImagePainter(R.drawable.logo),
//                        contentDescription = "Profile Photo",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(120.dp)
//                            .clip(CircleShape)
//                            .border(
//                                width = 2.dp,
//                                color = HotPink,
//                                shape = CircleShape
//                            )
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(
//                        "Welcome to PrettyNBeauty",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//
//                    Text(
//                        "Book your beauty services with us",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Pink80
//                    )
//
//                    if (isLoggedIn && userPhoneNumber.isNotEmpty()) {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            userPhoneNumber,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = HotPink,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//                }
//
//                // Services Section
//                ProfileSection(title = "My Services") {
//                    ProfileItem(
//                        icon = Icons.Default.Spa,
//                        title = "Hair Services",
//                        isExpanded = expandedItem == "Hair Services",
//                        description = "Professional hair treatments including cutting, coloring, and styling. Our expert stylists use premium products for best results.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "Hair Services") null else "Hair Services"
//                        }
//                    )
//                    ProfileItem(
//                        icon = Icons.Default.Face,
//                        title = "Skin Care",
//                        isExpanded = expandedItem == "Skin Care",
//                        description = "Facial treatments and skin therapies tailored to your skin type. Achieve glowing skin with our expert estheticians.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "Skin Care") null else "Skin Care"
//                        }
//                    )
//                    ProfileItem(
//                        icon = Icons.Default.Style,
//                        title = "Makeup",
//                        isExpanded = expandedItem == "Makeup",
//                        description = "Professional makeup services for all occasions. From natural looks to glamorous evening makeup, we've got you covered.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "Makeup") null else "Makeup"
//                        }
//                    )
//                }
//
//                // Support Section
//                ProfileSection(title = "Support") {
//                    ProfileItem(
//                        icon = Icons.Default.ContactSupport,
//                        title = "Contact Us",
//                        isExpanded = expandedItem == "Contact Us",
//                        description = "Reach at: bookingprettynbeauty@gmail.com. Our team is available 8AM-6PM daily.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "Contact Us") null else "Contact Us"
//                        }
//                    )
//                    ProfileItem(
//                        icon = Icons.Default.Info,
//                        title = "About Us",
//                        isExpanded = expandedItem == "About Us",
//                        description = "PrettyNBeauty has been providing premium beauty services since 2022. We believe in enhancing natural beauty with expert care.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "About Us") null else "About Us"
//                        }
//                    )
//                    ProfileItem(
//                        icon = Icons.Default.Policy,
//                        title = "Privacy Policy",
//                        isExpanded = expandedItem == "Privacy Policy",
//                        description = "We value your privacy. Your personal information is securely stored and never shared with third parties without consent.",
//                        onClick = {
//                            expandedItem = if (expandedItem == "Privacy Policy") null else "Privacy Policy"
//                        }
//                    )
//
//                    // Logout Button
//                    if (isLoggedIn) {
//                        Button(
//                            onClick = {
//                                isLoggedIn = false
//                                userPhoneNumber = ""
//                                // Navigate to splash activity
//                                navController.navigate("splash") {
//                                    popUpTo(navController.graph.startDestinationId) {
//                                        inclusive = true
//                                    }
//                                }
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            shape = RoundedCornerShape(12.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = HotPink
//                            )
//                        ) {
//                            Text(
//                                "Logout",
//                                modifier = Modifier.padding(vertical = 8.dp),
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(24.dp))
//            }
//        }
//    }
//}
//
//@Composable
//fun PhoneLoginDialog(
//    onDismiss: () -> Unit,
//    onLoginSuccess: (String) -> Unit
//) {
//    var phoneNumber by remember { mutableStateOf("") }
//    var otp by remember { mutableStateOf("") }
//    var showOtpField by remember { mutableStateOf(false) }
//    var countdown by remember { mutableStateOf(60) }
//    var isResendEnabled by remember { mutableStateOf(false) }
//
//    Dialog(onDismissRequest = onDismiss) {
//        Card(
//            modifier = Modifier
//                .width(320.dp)
//                .padding(8.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = Color.White
//            )
//        ) {
//            Column(
//                modifier = Modifier.padding(20.dp)
//            ) {
//                Text(
//                    "Login with Phone",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//
//                if (!showOtpField) {
//                    // Phone number input
//                    OutlinedTextField(
//                        value = phoneNumber,
//                        onValueChange = {
//                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
//                                phoneNumber = it
//                            }
//                        },
//                        label = { Text("Phone Number") },
//                        placeholder = { Text("Enter 10 digit number") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink,
//                            cursorColor = HotPink
//                        ),
//                        modifier = Modifier.fillMaxWidth(),
//                        prefix = {
//                            Text(
//                                "+91 ",
//                                color = Color.Gray,
//                                modifier = Modifier.padding(end = 4.dp)
//                            )
//                        }
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = {
//                            if (phoneNumber.length == 10) {
//                                // Send OTP logic would go here
//                                showOtpField = true
//                                // Start countdown timer
//                                isResendEnabled = false
//                                countdown = 60
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        enabled = phoneNumber.length == 10,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink
//                        )
//                    ) {
//                        Text("Send OTP")
//                    }
//                } else {
//                    // OTP input
//                    Text(
//                        "Enter OTP sent to +91 $phoneNumber",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//
//                    OutlinedTextField(
//                        value = otp,
//                        onValueChange = {
//                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
//                                otp = it
//                            }
//                        },
//                        label = { Text("OTP") },
//                        placeholder = { Text("Enter 6 digit OTP") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink,
//                            cursorColor = HotPink
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            if (isResendEnabled) "Didn't receive OTP?" else "Resend OTP in $countdown seconds",
//                            color = if (isResendEnabled) HotPink else Color.Gray,
//                            fontSize = 14.sp
//                        )
//
//                        Text(
//                            "Resend",
//                            color = if (isResendEnabled) HotPink else Color.LightGray,
//                            modifier = Modifier
//                                .clickable(enabled = isResendEnabled) {
//                                    // Resend OTP logic
//                                    isResendEnabled = false
//                                    countdown = 60
//                                },
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = {
//                            if (otp.length == 6) {
//                                // Verify OTP logic would go here
//                                // For demo, we'll just assume it's successful
//                                onLoginSuccess(phoneNumber)
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        enabled = otp.length == 6,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink
//                        )
//                    ) {
//                        Text("Verify & Login")
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(
//                    onClick = onDismiss,
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.LightGray
//                    )
//                ) {
//                    Text("Cancel", color = Color.DarkGray)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ProfileSection(title: String, content: @Composable () -> Unit) {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column {
//            Text(
//                text = title,
//                style = MaterialTheme.typography.titleSmall,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark,
//                modifier = Modifier.padding(16.dp)
//            )
//
//            content()
//        }
//    }
//}
//
//@Composable
//fun ProfileItem(
//    icon: ImageVector,
//    title: String,
//    isExpanded: Boolean,
//    description: String,
//    onClick: () -> Unit
//) {
//    Column {
//        ListItem(
//            modifier = Modifier.clickable { onClick() },
//            leadingContent = {
//                Icon(
//                    imageVector = icon,
//                    contentDescription = title,
//                    tint = HotPink
//                )
//            },
//            headlineContent = {
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = HotPinkDark
//                )
//            },
//            trailingContent = {
//                Icon(
//                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
//                    contentDescription = if (isExpanded) "Collapse" else "Expand",
//                    tint = Pink80
//                )
//            }
//        )
//
//        AnimatedVisibility(
//            visible = isExpanded,
//            enter = fadeIn() + expandVertically(),
//            exit = fadeOut() + shrinkVertically()
//        ) {
//            Text(
//                text = description,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Gray,
//                modifier = Modifier.padding(
//                    start = 72.dp,
//                    end = 16.dp,
//                    bottom = 16.dp
//                )
//            )
//        }
//
//        Divider(
//            modifier = Modifier.padding(horizontal = 16.dp),
//            thickness = 0.5.dp,
//            color = Pink10
//        )
//    }
//}












package com.beauty.parler.activity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.beauty.parler.R
import com.beauty.parler.ui.theme.HotPink
import com.beauty.parler.ui.theme.HotPinkDark
import com.beauty.parler.ui.theme.Pink10
import com.beauty.parler.ui.theme.Pink80
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var expandedItem by remember { mutableStateOf<String?>(null) }
    var isLoggedIn by remember { mutableStateOf(false) }
    var showLoginDialog by remember { mutableStateOf(false) }
    var userPhoneNumber by remember { mutableStateOf("") }

    if (showLoginDialog) {
        PhoneLoginDialog(
            onDismiss = { showLoginDialog = false },
            onLoginSuccess = { phoneNumber ->
                isLoggedIn = true
                userPhoneNumber = phoneNumber
                showLoginDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "My Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                actions = {
                    if (!isLoggedIn) {
                        Text(
                            "Login",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { showLoginDialog = true },
                            color = HotPink,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Pink10, Color.White),
                            startY = 0f,
                            endY = 500f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.logo),
                        contentDescription = "Profile Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = HotPink,
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Welcome to PrettyNBeauty",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )

                    Text(
                        "Book your beauty services with us",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Pink80
                    )

                    if (isLoggedIn && userPhoneNumber.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            userPhoneNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            color = HotPink,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Services Section
                ProfileSection(title = "My Services") {
                    ProfileItem(
                        icon = Icons.Default.Spa,
                        title = "Hair Services",
                        isExpanded = expandedItem == "Hair Services",
                        description = "Professional hair treatments including cutting, coloring, and styling. Our expert stylists use premium products for best results.",
                        onClick = {
                            expandedItem = if (expandedItem == "Hair Services") null else "Hair Services"
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Face,
                        title = "Skin Care",
                        isExpanded = expandedItem == "Skin Care",
                        description = "Facial treatments and skin therapies tailored to your skin type. Achieve glowing skin with our expert estheticians.",
                        onClick = {
                            expandedItem = if (expandedItem == "Skin Care") null else "Skin Care"
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Style,
                        title = "Makeup",
                        isExpanded = expandedItem == "Makeup",
                        description = "Professional makeup services for all occasions. From natural looks to glamorous evening makeup, we've got you covered.",
                        onClick = {
                            expandedItem = if (expandedItem == "Makeup") null else "Makeup"
                        }
                    )
                }

                // Support Section
                ProfileSection(title = "Support") {
                    ProfileItem(
                        icon = Icons.Default.ContactSupport,
                        title = "Contact Us",
                        isExpanded = expandedItem == "Contact Us",
                        description = "Reach at: bookingprettynbeauty@gmail.com. Our team is available 8AM-6PM daily.",
                        onClick = {
                            expandedItem = if (expandedItem == "Contact Us") null else "Contact Us"
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Info,
                        title = "About Us",
                        isExpanded = expandedItem == "About Us",
                        description = "PrettyNBeauty has been providing premium beauty services since 2022. We believe in enhancing natural beauty with expert care.",
                        onClick = {
                            expandedItem = if (expandedItem == "About Us") null else "About Us"
                        }
                    )
                    ProfileItem(
                        icon = Icons.Default.Policy,
                        title = "Privacy Policy",
                        isExpanded = expandedItem == "Privacy Policy",
                        description = "We value your privacy. Your personal information is securely stored and never shared with third parties without consent.",
                        onClick = {
                            expandedItem = if (expandedItem == "Privacy Policy") null else "Privacy Policy"
                        }
                    )

                    // Logout Button
                    if (isLoggedIn) {
                        Button(
                            onClick = {
                                isLoggedIn = false
                                userPhoneNumber = ""
                                // Navigate to main activity
                                navController.navigate("main") {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HotPink
                            )
                        ) {
                            Text(
                                "Logout",
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun PhoneLoginDialog(
    onDismiss: () -> Unit,
    onLoginSuccess: (String) -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var showOtpField by remember { mutableStateOf(false) }
    var countdown by remember { mutableStateOf(60) }
    var isResendEnabled by remember { mutableStateOf(false) }
    var generatedOtp by remember { mutableStateOf("") }

    // Countdown timer effect
    LaunchedEffect(key1 = showOtpField, key2 = countdown) {
        if (showOtpField && countdown > 0) {
            delay(1000)
            countdown--
            if (countdown == 0) {
                isResendEnabled = true
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    "Login with Phone",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (!showOtpField) {
                    // Phone number input
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                phoneNumber = it
                            }
                        },
                        label = { Text("Phone Number") },
                        placeholder = { Text("Enter 10 digit number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HotPink,
                            focusedLabelColor = HotPink,
                            cursorColor = HotPink
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        prefix = {
                            Text(
                                "+91 ",
                                color = Color.Gray,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (phoneNumber.length == 10) {
                                // Generate a random 6-digit OTP
                                generatedOtp = (100000..999999).random().toString()
                                // In a real app, you would send this OTP to the user's phone
                                // For demo purposes, we'll just show it in the UI
                                showOtpField = true
                                // Start countdown timer
                                isResendEnabled = false
                                countdown = 60
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = phoneNumber.length == 10,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink
                        )
                    ) {
                        Text("Send OTP")
                    }
                } else {
                    // Show the generated OTP for demo purposes
                    // In a real app, you would remove this
                    Text(
                        "Demo OTP: $generatedOtp",
                        color = HotPink,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // OTP input
                    Text(
                        "Enter OTP sent to +91 $phoneNumber",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                                otp = it
                            }
                        },
                        label = { Text("OTP") },
                        placeholder = { Text("Enter 6 digit OTP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HotPink,
                            focusedLabelColor = HotPink,
                            cursorColor = HotPink
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            if (isResendEnabled) "Didn't receive OTP?" else "Resend OTP in ${countdown}s",
                            color = if (isResendEnabled) HotPink else Color.Gray,
                            fontSize = 14.sp
                        )

                        Text(
                            "Resend",
                            color = if (isResendEnabled) HotPink else Color.LightGray,
                            modifier = Modifier
                                .clickable(enabled = isResendEnabled) {
                                    // Resend OTP logic
                                    generatedOtp = (100000..999999).random().toString()
                                    isResendEnabled = false
                                    countdown = 60
                                },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (otp == generatedOtp) {
                                // Verify OTP - if matches, login successful
                                onLoginSuccess(phoneNumber)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = otp.length == 6,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink
                        )
                    ) {
                        Text("Verify & Login")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text("Cancel", color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun ProfileSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark,
                modifier = Modifier.padding(16.dp)
            )

            content()
        }
    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    title: String,
    isExpanded: Boolean,
    description: String,
    onClick: () -> Unit
) {
    Column {
        ListItem(
            modifier = Modifier.clickable { onClick() },
            leadingContent = {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = HotPink
                )
            },
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HotPinkDark
                )
            },
            trailingContent = {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Pink80
                )
            }
        )

        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(
                    start = 72.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp,
            color = Pink10
        )
    }
}