//package com.beauty.parler.activity
//
//import android.util.Log
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.beauty.parler.ui.theme.*
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.tasks.await
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun OffersScreen(navController: NavHostController) {
//    var selectedOffer by remember { mutableStateOf<Offer?>(null) }
//    var offers by remember { mutableStateOf<List<Offer>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    // Fire store instance
//    val db = Firebase.firestore
//
//    // Fetch offers from Fire store
//    LaunchedEffect(Unit) {
//        try {
//            val result = db.collection("offers").get().await()
//            val tempOffers = mutableListOf<Offer>()
//
//            result.documents.forEach { doc ->
//                try {
//                    val offer = Offer(
//                        id = doc.id,
//                        name = doc.getString("name") ?: "On all Packages & Services",
//                        title = doc.getString("title") ?: "Special Offer",
//                        description = doc.getString("description") ?: "",
//                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
//                        formattedDate = doc.getString("formattedDate")?.replace(",", "") ?: "",
//                        useCode = doc.getString("useCode") ?: "",
//                        originalPrice = doc.getLong("originalPrice")?.toInt(),
//                        discountedPrice = doc.getLong("discountedPrice")?.toInt(),
//                        usedCount = doc.getLong("usedCount")?.toInt() ?: 0,
//                        remainingCount = doc.getLong("remainingCount")?.toInt() ?: 100,
//                        imageUrl = doc.getString("imageUrl") ?: ""
//                    )
//                    tempOffers.add(offer)
//
//                    Log.d("OfferData", "Loaded offer: ${offer.title}, Discount: ${offer.discountPercentage}%")
//                } catch (e: Exception) {
//                    Log.e("OffersScreen", "Error parsing document ${doc.id}", e)
//                }
//            }
//
//            offers = tempOffers
//            if (offers.isEmpty()) {
//                errorMessage = "No offers found"
//            }
//        } catch (e: Exception) {
//            Log.e("OffersScreen", "Error fetching offers", e)
//            errorMessage = "Failed to load offers. Please try again."
//        } finally {
//            isLoading = false
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "Special Offers",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.White,
//                    scrolledContainerColor = Color.White
//                ),
//                actions = {
////                    IconButton(onClick = { /* Filter */ }) {
////                        Icon(
////                            imageVector = Icons.Default.ShoppingCart,
////                            contentDescription = "Offers",
////                            tint = HotPink
////                        )
////                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.fillMaxSize()) {
//            // Background gradient
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
//            when {
//                isLoading -> {
//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
////                        CircularProgressIndicator(color = HotPink)
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Text("Loading...", color = Color.Black)
//                    }
//                }
//                errorMessage != null -> {
//                    Column(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            errorMessage ?: "Unknown error",
//                            color = HotPinkDark,
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Button(
//                            onClick = {
//                                isLoading = true
//                                errorMessage = null
//                                offers = emptyList()
//                            },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = HotPink,
//                                contentColor = Color.White
//                            )
//                        ) {
//                            Text("Retry")
//                        }
//                    }
//                }
//                else -> {
//                    LazyColumn(
//                        modifier = Modifier
//                            .padding(innerPadding)
//                            .fillMaxSize()
//                    ) {
//                        item {
//                            Text(
//                                text = "Limited Time Offers",
//                                style = MaterialTheme.typography.titleMedium,
//                                fontWeight = FontWeight.Bold,
//                                color = HotPinkDark,
//                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                            )
//
//                            Text(
//                                text = "Avail these exclusive deals and save on your beauty services",
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = Pink80,
//                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
//                            )
//
//                            Spacer(modifier = Modifier.height(8.dp))
//                        }
//
//                        // Featured offer (first offer)
//                        if (offers.isNotEmpty()) {
//                            item {
//                                Card(
//                                    modifier = Modifier
//                                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                                        .fillMaxWidth(),
//                                    shape = RoundedCornerShape(20.dp),
//                                    colors = CardDefaults.cardColors(
//                                        containerColor = Color.White
//                                    ),
//                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(200.dp)
//                                            .background(
//                                                brush = Brush.verticalGradient(
//                                                    colors = listOf(HotPink, Pink80)
//                                                )
//                                            )
//                                    ) {
//                                        Column(
//                                            modifier = Modifier
//                                                .fillMaxSize()
//                                                .padding(16.dp)
//                                        ) {
//                                            Text(
//                                                "FLAT ${offers.first().discountPercentage}% OFF",
//                                                style = MaterialTheme.typography.displaySmall,
//                                                fontWeight = FontWeight.Bold,
//                                                color = Color.White
//                                            )
//
//                                            Text(
//                                                offers.first().name,
//                                                style = MaterialTheme.typography.titleMedium,
//                                                color = Color.White
//                                            )
//
//                                            Spacer(modifier = Modifier.weight(1f))
//
//                                            if (offers.first().useCode.isNotEmpty()) {
//                                                Text(
//                                                    "Use code: ${offers.first().useCode}",
//                                                    style = MaterialTheme.typography.titleMedium,
//                                                    fontWeight = FontWeight.Bold,
//                                                    color = Color.White
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        // All offers
//                        items(offers) { offer ->
//                            OfferCard(
//                                offer = offer,
//                                onCardClick = { selectedOffer = offer }
//                            )
//                        }
//
//                        item {
//                            Spacer(modifier = Modifier.height(80.dp))
//                        }
//                    }
//
//                    // Floating Book Now button
//                    AnimatedVisibility(
//                        visible = selectedOffer != null,
//                        enter = fadeIn() + slideInVertically { it },
//                        exit = fadeOut() + slideOutVertically { it },
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .padding(bottom = 16.dp)
//                    ) {
//                        Button(
//                            onClick = { /* Book offer */ },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 24.dp)
//                                .height(50.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = HotPink,
//                                contentColor = Color.White
//                            ),
//                            shape = RoundedCornerShape(12.dp),
//                            elevation = ButtonDefaults.buttonElevation(
//                                defaultElevation = 8.dp,
//                                pressedElevation = 4.dp
//                            )
//                        ) {
//                            Text(
//                                "Avail ${selectedOffer?.discountPercentage}% Offer",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 16.sp
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun OfferCard(offer: Offer, onCardClick: () -> Unit) {
//    var isExpanded by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .clickable { onCardClick() },
//        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        ),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier.animateContentSize()
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .background(
//                        brush = Brush.horizontalGradient(
//                            colors = listOf(Pink20, Pink10)
//                        )
//                    )
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            "${offer.discountPercentage}% OFF",
//                            style = MaterialTheme.typography.displaySmall,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPinkDark
//                        )
//
//                        Text(
//                            offer.title,
//                            style = MaterialTheme.typography.titleMedium,
//                            color = HotPink
//                        )
//
//                        if (offer.originalPrice != null) {
//                            Text(
//                                "₹${offer.originalPrice}",
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = Color.Gray,
//                                textDecoration = TextDecoration.LineThrough
//                            )
//                        }
//                    }
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
////                    Text(
////                        "Valid until ${offer.formattedDate}",
////                        style = MaterialTheme.typography.labelMedium,
////                        color = Pink80
////                    )
//
//                    if (offer.useCode.isNotEmpty()) {
//                        Text(
//                            "Use code: ${offer.useCode}",
//                            style = MaterialTheme.typography.bodyMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPinkDark
//                        )
//                    }
//                }
//            }
//
//            if (isExpanded) {
//                Divider(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    color = Pink20,
//                    thickness = 1.dp
//                )
//
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(
//                        offer.description,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            "${offer.usedCount} people used this",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = Pink80
//                        )
//
//                        Text(
//                            "Only ${offer.remainingCount} left",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = HotPink
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    LinearProgressIndicator(
//                        progress = offer.usedCount.toFloat() / (offer.usedCount + offer.remainingCount),
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(8.dp),
//                        color = HotPink,
//                        trackColor = Pink20
//                    )
//                }
//            }
//        }
//    }
//}
//
//data class Offer(
//    val id: String = "",
//    val name: String = "",
//    val title: String = "",
//    val description: String = "",
//    val discountPercentage: Int = 0,
//    val formattedDate: String = "",
//    val useCode: String = "",
//    val originalPrice: Int? = null,
//    val discountedPrice: Int? = null,
//    val usedCount: Int = 0,
//    val remainingCount: Int = 100,
//    val imageUrl: String
//)
//
//@Preview(showBackground = true)
//@Composable
//fun OffersScreenPreview() {
//    PARLERTheme {
//        OffersScreen(rememberNavController())
//    }
//}





















package com.beauty.parler.activity

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.beauty.parler.R
import com.beauty.parler.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(navController: NavHostController) {
    var selectedOffer by remember { mutableStateOf<Offer?>(null) }
    var offers by remember { mutableStateOf<List<Offer>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fire store instance
    val db = Firebase.firestore

    // Fetch offers from Fire store
    LaunchedEffect(Unit) {
        try {
            val result = db.collection("offers").get().await()
            val tempOffers = mutableListOf<Offer>()

            result.documents.forEach { doc ->
                try {
                    val offer = Offer(
                        id = doc.id,
                        name = doc.getString("name") ?: "On all Packages & Services",
                        title = doc.getString("title") ?: "Special Offer",
                        description = doc.getString("description") ?: "",
                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
                        formattedDate = doc.getString("formattedDate")?.replace(",", "") ?: "",
                        useCode = doc.getString("useCode") ?: "",
                        originalPrice = doc.getLong("originalPrice")?.toInt(),
                        discountedPrice = doc.getLong("discountedPrice")?.toInt(),
                        usedCount = doc.getLong("usedCount")?.toInt() ?: 0,
                        remainingCount = doc.getLong("remainingCount")?.toInt() ?: 100,
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                    tempOffers.add(offer)

                    Log.d("OfferData", "Loaded offer: ${offer.title}, Discount: ${offer.discountPercentage}%")
                } catch (e: Exception) {
                    Log.e("OffersScreen", "Error parsing document ${doc.id}", e)
                }
            }

            offers = tempOffers
            if (offers.isEmpty()) {
                errorMessage = "No offers found"
            }
        } catch (e: Exception) {
            Log.e("OffersScreen", "Error fetching offers", e)
            errorMessage = "Failed to load offers. Please try again."
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Special Offers",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                actions = {
                    // IconButton(onClick = { /* Filter */ }) {
                    //     Icon(
                    //         imageVector = Icons.Default.ShoppingCart,
                    //         contentDescription = "Offers",
                    //         tint = HotPink
                    //     )
                    // }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background gradient
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

            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = HotPink)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading offers...", color = HotPinkDark)
                    }
                }
                errorMessage != null -> {
                    if (offers.isEmpty()) {
                        // Show special "Coming Soon" animation when no offers are available
                        SpecialOffersComingSoon()
                    } else {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                errorMessage ?: "Unknown error",
                                color = HotPinkDark,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    isLoading = true
                                    errorMessage = null
                                    offers = emptyList()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HotPink,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        item {
                            Text(
                                text = "Limited Time Offers",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = HotPinkDark,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                            Text(
                                text = "Avail these exclusive deals and save on your beauty services",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Pink80,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Featured offer (first offer)
                        if (offers.isNotEmpty()) {
                            item {
                                Card(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(HotPink, Pink80)
                                                )
                                            )
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                "FLAT ${offers.first().discountPercentage}% OFF",
                                                style = MaterialTheme.typography.displaySmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )

                                            Text(
                                                offers.first().name,
                                                style = MaterialTheme.typography.titleMedium,
                                                color = Color.White
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            if (offers.first().useCode.isNotEmpty()) {
                                                Text(
                                                    "Use code: ${offers.first().useCode}",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // All offers
                        items(offers) { offer ->
                            OfferCard(
                                offer = offer,
                                onCardClick = { selectedOffer = offer }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }

                    // Floating Book Now button
                    AnimatedVisibility(
                        visible = selectedOffer != null,
                        enter = fadeIn() + slideInVertically { it },
                        exit = fadeOut() + slideOutVertically { it },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        Button(
                            onClick = { /* Book offer */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HotPink,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 4.dp
                            )
                        ) {
                            Text(
                                "Avail ${selectedOffer?.discountPercentage}% Offer",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpecialOffersComingSoon() {
    // Animation values
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.8f at 0
                1.1f at 1000
                1f at 2000
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.3f at 0
                1f at 1000
                0.3f at 2000
            }
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated decorative circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Pink40, Pink10, Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(0.5f, 0.5f),
                        radius = 300f
                    )
                )
        ) {
            // Rotating sparkles
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(rotation)
            ) {
                for (i in 0 until 8) {
                    Icon(
                        imageVector = Icons.Filled.Spa,
                        contentDescription = null,
                        tint = HotPink.copy(alpha = 0.6f),
                        modifier = Modifier
                            .offset(
                                x = (80 * Math.cos(i * Math.PI / 4)).toFloat().dp,
                                y = (80 * Math.sin(i * Math.PI / 4)).toFloat().dp
                            )
                            .size(24.dp)
                    )
                }
            }

            // Central beauty icon
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), //beauty icon
                contentDescription = "Beauty Icon",
                tint = HotPink,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                    .scale(scale)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Animated text
        Text(
            "Special Offers\nComing Soon!",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = HotPinkDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(alpha)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "We're preparing something beautiful for you.\nStay tuned for exclusive deals!",
            style = MaterialTheme.typography.bodyLarge,
            color = Pink80,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Animated button
        Button(
            onClick = { /* Notify me action */ },
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HotPink,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Spa,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Notify Me", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun OfferCard(offer: Offer, onCardClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.animateContentSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Pink20, Pink10)
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "${offer.discountPercentage}% OFF",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = HotPinkDark
                        )

                        Text(
                            offer.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = HotPink
                        )

                        if (offer.originalPrice != null) {
                            Text(
                                "₹${offer.originalPrice}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    if (offer.useCode.isNotEmpty()) {
                        Text(
                            "Use code: ${offer.useCode}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = HotPinkDark
                        )
                    }
                }
            }

            if (isExpanded) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Pink20,
                    thickness = 1.dp
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        offer.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${offer.usedCount} people used this",
                            style = MaterialTheme.typography.labelSmall,
                            color = Pink80
                        )

                        Text(
                            "Only ${offer.remainingCount} left",
                            style = MaterialTheme.typography.labelSmall,
                            color = HotPink
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = offer.usedCount.toFloat() / (offer.usedCount + offer.remainingCount),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = HotPink,
                        trackColor = Pink20
                    )
                }
            }
        }
    }
}

data class Offer(
    val id: String = "",
    val name: String = "",
    val title: String = "",
    val description: String = "",
    val discountPercentage: Int = 0,
    val formattedDate: String = "",
    val useCode: String = "",
    val originalPrice: Int? = null,
    val discountedPrice: Int? = null,
    val usedCount: Int = 0,
    val remainingCount: Int = 100,
    val imageUrl: String = ""
)

@Preview(showBackground = true)
@Composable
fun OffersScreenPreview() {
    PARLERTheme {
        OffersScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun SpecialOffersComingSoonPreview() {
    PARLERTheme {
        SpecialOffersComingSoon()
    }
}