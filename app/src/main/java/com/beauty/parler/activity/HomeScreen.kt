package com.beauty.parler.activity

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.beauty.parler.R
import com.beauty.parler.model.BeautyPackage
import com.beauty.parler.model.Service
import com.beauty.parler.shared.CartViewModel
import com.beauty.parler.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.vector.ImageVector

@RequiresApi(35)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    val scrollState = rememberScrollState()
    val showFab by remember { derivedStateOf { scrollState.value > 200 } }
    val cartItemCount = remember { mutableStateOf(3) }

    Scaffold(
//        topBar = { AppTopBar(cartItemCount.value) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Pink10)
                .padding(padding)
        ) {
            AnimatedHeader()
            ServedWomen()
            OfferBannerImage()
            TrendingTreatmentsSection(navController)
            LuxuryCollectionsSection(navController)
            BeautyTipsSection()
            TestimonialsSection()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun AnimatedHeader() {
    val infiniteTransition = rememberInfiniteTransition()

//    val pulse by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 1.05f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(5000, easing = FastOutSlowInEasing),
//            repeatMode = RepeatMode.Reverse
//        ), label = ""
//    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(460.dp)
            .padding(start = 11.dp, end = 11.dp, bottom = 8.dp)

//            .scale(pulse)
    ) {
//        val infiniteFloat by infiniteTransition.animateFloat(
//            initialValue = 0f,
//            targetValue = 10f,
//            animationSpec = infiniteRepeatable(
//                animation = tween(00,0, easing = FastOutSlowInEasing),
//                repeatMode = RepeatMode.Reverse
//            ), label = ""
//        )

        AsyncImage(
            model = "https://i.postimg.cc/Pr1jsf7H/Whats-App-Image-2025-08-06-at-11-34-51-18ae752e.jpg",
            contentDescription = "Beauty Service",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
//                .offset(y = infiniteFloat.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f)),
                        startY = 0.3f
                    )
                )
        )
    }
}


@Composable
fun ServedWomen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main heading
        Text(
            text = "Salon at Home",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = HotPinkDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Rating section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star rating",
                tint = Color(0xFFFFD700), // Gold color for stars
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "4.9/5",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "2500+ women reviews",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Location
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = HotPink,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Jamshedpur",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Service count with decorative elements
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Pink10, Color.White, Pink10)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Spa,
                    contentDescription = "Services",
                    tint = HotPink,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "4000+ women served",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Decorative divider
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(2.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, HotPink, Color.Transparent)
                    )
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tagline
        Text(
            text = "Experience luxury beauty services in the comfort of your home",
            style = MaterialTheme.typography.bodyLarge,
            color = Pink80,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

//
//
//// offer banner image
//@Composable
//fun OfferBannerImage() {
//    val offers = remember { mutableStateOf<List<Offer>>(emptyList()) }
//    val isLoading = remember { mutableStateOf(true) }
//    val context = LocalContext.current
//    var hasLoaded by remember { mutableStateOf(false) } // Track if we've already loaded
//
//    // Load offers from Firestore  only once
//    LaunchedEffect(Unit) {
//        if (hasLoaded) return@LaunchedEffect // Don't load again if already loaded
//
//        try {
//            val db = Firebase.firestore
//            val result = db.collection("offers")
//                .whereGreaterThan("_validDate", System.currentTimeMillis())
//                .get()
//                .await()
//
//            val offersList = result.documents.mapNotNull { doc ->
//                try {
//                    Offer(
//                        id = doc.id,
//                        imageUrl = doc.getString("imageUrl") ?: ""
//                    )
//                } catch (e: Exception) {
//                    null
//                }
//            }
//
//            offers.value = offersList
//            hasLoaded = true // Mark as loaded
////            Log.d("OfferBannerImage", "Loaded ${offersList.size} offers")
//        } catch (e: Exception) {
////            Log.e("OfferBannerImage", "Error loading offers: ${e.message}")
//        } finally {
//            isLoading.value = false
//        }
//    }
//
//    // Show the first offer image
//    if (offers.value.isNotEmpty() && offers.value[0].imageUrl.isNotEmpty()) {
//        AsyncImage(
//            model = offers.value[0].imageUrl,
//            contentDescription = "",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(70.dp)
//                .padding(start = 12.dp, end = 12.dp)
//                .clickable {
//                    // Click
//                }
//        )
//    } else if (!isLoading.value) {
//       //
//    }
//}

// Animated offer banner with falling flowers
@RequiresApi(35)
@Composable
fun OfferBannerImage() {
    val offers = remember { mutableStateOf<List<Offer>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val context = LocalContext.current
    var hasLoaded by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { offers.value.size })

    // State for falling flowers animation
//    val flowers = remember { mutableStateListOf<FlowerPetal>() }

    // Generate falling flowers every 1-1.5 seconds
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay((1000L..1500L).random()) // Random delay between 1-1.5 seconds
//            if (offers.value.isNotEmpty()) {
//                flowers.add(
//                    FlowerPetal(
//                        id = System.currentTimeMillis(),
//                        x = (0..100).random().toFloat(), // Random horizontal position
//                        size = (20..40).random().dp, // Random size
//                        rotation = (0..360).random().toFloat(), // Random rotation
//                        duration = (3000..5000).random().toLong() // Random fall duration
//                    )
//                )
//
//                // Remove old flowers after a while
//                if (flowers.size > 55) {
//                    flowers.removeFirst()
//                }
//            }
//        }
//    }

    // Auto-scroll effect
    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(1000) // every 1 seconds
            if (offers.value.isNotEmpty()) {
                val nextPage = (pagerState.currentPage + 1) % offers.value.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    // Load offers from Firestore only once
    LaunchedEffect(Unit) {
        if (hasLoaded) return@LaunchedEffect

        try {
            val db = Firebase.firestore
            val result = db.collection("offers")
                .whereGreaterThan("_validDate", System.currentTimeMillis())
                .get()
                .await()

            val offersList = result.documents.mapNotNull { doc ->
                try {
                    Offer(
                        id = doc.id,
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }

            offers.value = offersList
            hasLoaded = true
        } catch (e: Exception) {
            // Handle error
        } finally {
            isLoading.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Falling flowers animation
//        flowers.forEach { flower ->
//            FallingFlower(
//                flower = flower,
//                onRemove = { flowers.remove(flower) }
//            )
//        }

        if (offers.value.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp)
                    ) {
                        val offer = offers.value[page]
                        AsyncImage(
                            model = offer.imageUrl,
                            contentDescription = "Special Offer",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    // Handle offer click
                                }
                        )

                        // Decorative border
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .border(
                                    width = 0.2.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(HotPink, Color.White, HotPink)
                                    ),
                                    shape = RoundedCornerShape(14.dp)
                                )
                        )
                    }
                }

                // Page indicators
                if (offers.value.size > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(offers.value.size) { index ->
                            AnimatedVisibility(
                                visible = index == pagerState.currentPage,
                                enter = scaleIn() + fadeIn(),
                                exit = scaleOut() + fadeOut()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(10.dp)
                                        .background(
                                            color = HotPink,
                                            shape = CircleShape
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                )
                            }
                            AnimatedVisibility(
                                visible = index != pagerState.currentPage,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(8.dp)
                                        .background(
                                            color = Color.LightGray.copy(alpha = 0.7f),
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                }
            }
        } else if (!isLoading.value) {
            // Animated placeholder when no offers are available
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 12.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Pink10, Color.White, Pink10)
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(HotPink, Color.White, HotPink)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🌟 Special Offers Coming Soon 🌟",
                    color = HotPinkDark,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}
//
//// Data class for flower petals
//data class FlowerPetal(
//    val id: Long,
//    val x: Float, // 0-100 percentage
//    val size: Dp,
//    val rotation: Float,
//    val duration: Long
//)
//
//// Falling flower composable
//@Composable
//fun FallingFlower(flower: FlowerPetal, onRemove: () -> Unit) {
//    var visible by remember { mutableStateOf(true) }
//    val infiniteTransition = rememberInfiniteTransition()
//
//    val fallAnimation by infiniteTransition.animateFloat(
//        initialValue = -50f,
//        targetValue = 150f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = flower.duration.toInt(),
//                easing = LinearEasing
//            ),
//            repeatMode = RepeatMode.Restart
//        ), label = ""
//    )
//
//    val rotationAnimation by infiniteTransition.animateFloat(
//        initialValue = flower.rotation,
//        targetValue = flower.rotation + 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = (flower.duration * 2).toInt(),
//                easing = LinearEasing
//            )
//        ), label = ""
//    )
//
//    val alphaAnimation by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 0f,
//        animationSpec = infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = flower.duration.toInt()
//                0.7f at (flower.duration * 0.7f).toInt()
//            }
//        ), label = ""
//    )
//
//    if (visible) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .absoluteOffset(
//                    x = (flower.x / 100f) * LocalConfiguration.current.screenWidthDp.dp,
//                    y = fallAnimation.dp
//                )
//        ) {
//            Icon(
//                imageVector = Icons.Default.LocalFlorist,
//                contentDescription = null,
//                tint = HotPink.copy(alpha = alphaAnimation),
//                modifier = Modifier
//                    .size(flower.size)
//                    .rotate(rotationAnimation)
//            )
//        }
//
//        // Remove flower when animation completes
//        LaunchedEffect(flower.id) {
//            delay(flower.duration)
//            visible = false
//            onRemove()
//        }
//    }
//}


@Composable
fun TrendingTreatmentsSection(navController: NavHostController) {
    var services by remember { mutableStateOf<List<Service>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val db = Firebase.firestore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val result = db.collection("services")
                    .limit(3)
                    .get(com.google.firebase.firestore.Source.SERVER)
                    .await()

                services = result.documents.mapNotNull { doc ->
                    try {
                        Service(
                            id = doc.id,
                            name = doc.getString("name") ?: "Unnamed Service",
                            description = doc.getString("description") ?: "No description",
                            price = doc.getString("price")?.toDoubleOrNull() ?: 0.0,
                            duration = doc.getString("time")?.toIntOrNull() ?: 30,
                            rating = doc.getDouble("rating")?.toFloat() ?: 4.8f,
//                            reviews = (doc.get("reviews") as? Number)?.toInt() ?: 0,
                            imageUrl = doc.getString("imageUrl") ?: "",
                            categoryName = doc.getString("categoryName")?:"",
                            categoryId = doc.getString("categoryId")?:""
                        )
                    } catch (e: Exception) {
                        Log.e("ServiceMapping", "Error mapping service ${doc.id}", e)
                        null
                    }
                }
            } catch (e: Exception) {
                Log.e("TrendingTreatments", "Error loading services", e)
//                Toast.makeText(context, "Failed to load services", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Column(modifier = Modifier.padding(vertical = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Trending Treatments",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark,
                modifier = Modifier.weight(1f)
            )
//            TextButton(
//                onClick = { navController.navigate("services")
//                    popUpTo("home") { saveState = true } }) {
//                Text("See All", color = HotPink)
//            }
            TextButton(
                onClick = {
                    navController.navigate("services") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Pink80
                )
            ) {
                Text("See All")
            }
        }

        Text(
            "Most loved experiences this season",
            style = MaterialTheme.typography.bodyMedium,
            color = Pink80,
            modifier = Modifier.padding(horizontal = 25.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .width(330.dp)
                            .height(260.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f))
                    )
                }
            }
        } else if (services.isEmpty()) {
            Box(
                modifier = Modifier
                    .width(330.dp)
                    .height(260.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No services available", color = Pink80)
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(services) { service ->
                    ServiceCardHome(
                        service = service,
                        onCardClick = {
                            navController.navigate("services") {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceCardHome(
    service: Service,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(330.dp)
            .height(260.dp)
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            {
                AsyncImage(
                    model = service.imageUrl.ifEmpty { R.drawable.logo },
                    contentDescription = service.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                ) {
                    RatingChip(rating = service.rating)
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = service.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = HotPink.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${service.duration} mins",
                            style = MaterialTheme.typography.labelSmall,
                            color = HotPink,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "₹${service.price}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

//                Button(
//                    onClick = onCardClick,
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = HotPink,
//                        contentColor = Color.White
//                    ),
//                    shape = RoundedCornerShape(8.dp),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("View Details")
//                }
            }
        }
    }
}

// package section
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LuxuryCollectionsSection(navController: NavHostController) {
    var packages by remember { mutableStateOf<List<BeautyPackage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val db = Firebase.firestore
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Pink color scheme
    val hotPink = Color(0xFFFF69B4)
    val hotPinkDark = Color(0xFFC71585)
    val pink80 = Color(0xCCFF69B4)
    val pink20 = Color(0x33FF69B4)

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val result = db.collection("packages")
                    .limit(3)
                    .get()
                    .await()

                packages = result.documents.mapNotNull { doc ->
                    try {
                        val serviceNames =
                            (doc.get("serviceNames") as? List<String>)?.filterNot { it.isBlank() }
                                ?: emptyList()
                        val serviceImageUrls =
                            (doc.get("serviceImageUrls") as? List<String>) ?: emptyList()

                        val durations =
                            (doc.get("durations") as? List<Long>)?.map { it.toInt() } ?: emptyList()

                        BeautyPackage(
                            id = doc.id,
                            name = doc.getString("name") ?: "Unnamed Package",
                            description = doc.getString("description") ?: "No description",
                            imageUrl = doc.getString("packageImageUrl") ?: "",
                            price = (doc.getString("price") ?: "0.0").toDoubleOrNull() ?: 0.0,
                            servicesIncluded = serviceNames,
                            serviceImageUrls = serviceImageUrls,
                            rating = 0f,
//                            reviews = 0,
                            servicePrices = emptyList(),
                            servicesDetails = emptyList(),
                            durations = durations.toString()
                        )
                    } catch (e: Exception) {
                        Log.e("PackageMapping", "Error mapping package ${doc.id}", e)
                        null
                    }
                }
            } catch (e: Exception) {
                Log.e("LuxuryCollections", "Error loading packages", e)
//                Toast.makeText(context, "Failed to load packages", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Premium Packages",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = hotPinkDark,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = {
                    navController.navigate("services") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Pink80
                )
            ) {
                Text("See All")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Curated packages for complete beauty solutions",
            style = MaterialTheme.typography.bodyMedium,
            color = pink80,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .width(330.dp)
                            .height(250.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f))
                    )
                }
            }
        } else if (packages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No packages available", color = pink80)
            }
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(packages) { beautyPackage ->
                    PackageCardHome(
                        beautyPackage = beautyPackage,
                        onCardClick = {
                            // Navigate to services screen with package ID   /${beautyPackage.id}
                            navController.navigate("services") {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        hotPink = hotPink,
                        pink20 = pink20
                    )
                }
            }
        }
    }
}

@Composable
fun PackageCardHome(
    beautyPackage: BeautyPackage,
    onCardClick: () -> Unit,
    hotPink: Color,
    pink20: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(330.dp)
            .clickable(
                onClick = onCardClick,
//                indication = rememberRipple(bounded = true, color = hotPink)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                AsyncImage(
                    model = beautyPackage.imageUrl.ifEmpty { R.drawable.logo },
                    contentDescription = beautyPackage.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = beautyPackage.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = beautyPackage.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "₹${beautyPackage.price}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = hotPink
                    )

                    Text(
                        text = "${beautyPackage.servicesIncluded.size} services",
                        style = MaterialTheme.typography.bodySmall,
                        color = hotPink,
                        modifier = Modifier
                            .background(pink20, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
private fun RatingChip(rating: Float) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "%.1f".format(rating),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class BeautyTip(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val readMoreUrl: String
)

val beautyTips = listOf(
    BeautyTip(
        "Summer Skin Care",
        "Protect your skin from sun damage with these tips",
        Icons.Default.Spa,
        "https://example.com/summer-skin-care"
    ),
    BeautyTip(
        "Hair Fall Solutions",
        "Combat seasonal hair fall with these remedies",
        Icons.Default.ContentCut,
        "https://example.com/hair-fall"
    ),
    BeautyTip(
        "Makeup Longevity",
        "Make your makeup last all day in the heat",
        Icons.Default.Face,
        "https://example.com/makeup-longevity"
    ),
    BeautyTip(
        "Natural Remedies",
        "DIY beauty treatments with kitchen ingredients",
        Icons.Default.Eco,
        "https://example.com/natural-remedies"
    )
)

@Composable
fun BeautyTipsSection() {
    Column(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Text(
            "Beauty Tips",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HotPinkDark,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(beautyTips) { tip ->
                BeautyTipCard(tip)
            }
        }
    }
}

@Composable
fun BeautyTipCard(tip: BeautyTip) {
    var expanded by remember { mutableStateOf(false) }

    val detailedDescription = when (tip.title) {
        "Summer Skin Care" -> """
            ${tip.description}

            1. Use broad-spectrum SPF 30+ sunscreen daily
            2. Reapply sunscreen every 2 hours when outdoors
            3. Wear wide-brimmed hats and sunglasses
            4. Seek shade during peak sun hours (10am-4pm)
            5. Use antioxidant serums for extra protection
        """.trimIndent()

        "Hair Fall Solutions" -> """
            ${tip.description}

            1. Use sulfate-free shampoos
            2. Massage scalp with coconut oil weekly
            3. Take biotin supplements after consulting doctor
            4. Avoid tight hairstyles that pull on roots
            5. Use wide-tooth comb on wet hair
            6. Reduce heat styling tools usage
            7. Eat protein-rich foods for hair health
        """.trimIndent()

        "Makeup Longevity" -> """
            ${tip.description}

            1. Start with a clean, moisturized face
            2. Use primer suited for your skin type
            3. Apply foundation with damp beauty blender
            4. Set with translucent powder in T-zone
            5. Use waterproof mascara and eyeliner
            6. Carry blotting papers for touch-ups
            7. Finish with setting spray for all-day hold
        """.trimIndent()

        "Natural Remedies" -> """
            ${tip.description}

            1. Honey + yogurt mask for glowing skin
            2. Coconut oil for deep hair conditioning
            3. Cucumber slices to reduce under-eye puffiness
            4. Aloe vera gel for sunburn relief
            5. Sugar + olive oil scrub for exfoliation
            6. Green tea bags for reducing dark circles
            7. Apple cider vinegar as clarifying hair rinse
        """.trimIndent()

        else -> tip.description
    }

    Card(
        modifier = Modifier
            .width(330.dp)
            .heightIn(min = 220.dp, max = if (expanded) 400.dp else 220.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(HotPink.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        tip.icon,
                        contentDescription = "Beauty Tip",
                        tint = HotPink,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    tip.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                if (expanded) detailedDescription else tip.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = HotPink
                ),
                border = BorderStroke(1.dp, HotPink),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Text(if (expanded) "Show Less" else "Read More")
            }
        }
    }
}

data class Testimonial(
    val name: String,
    val initials: String,
    val rating: Int,
    val comment: String
)

val testimonials = listOf(
    Testimonial(
        "Priya Sharma",
        "PS",
        5,
        "The best at-home salon experience! The stylist was punctual and did an amazing job with my haircut. Will definitely book again!"
    ),
    Testimonial(
        "Rinki kumari",
        "RM",
        4,
        "Great service! The facial was very relaxing and my skin feels amazing. Would have given 5 stars if the products were a bit better."
    ),
    Testimonial(
        "Ananya Patel",
        "AP",
        5,
        "Absolutely loved my bridal makeup trial. The artist understood exactly what I wanted and executed it perfectly."
    ),
    Testimonial(
        "Neha Gupta",
        "NG",
        5,
        "Regular customer here! Their waxing services are the most painless I've ever experienced. Highly recommend!"
    )
)

@Composable
fun TestimonialsSection() {
    Column(
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Text(
            "What Our Clients Say",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HotPinkDark,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(testimonials) { testimonial ->
                TestimonialCard(testimonial)
            }
        }
    }
}

@Composable
fun TestimonialCard(testimonial: Testimonial) {
    Card(
        modifier = Modifier
            .width(330.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(HotPink.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        testimonial.initials,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HotPink
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        testimonial.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        repeat(testimonial.rating) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        repeat(5 - testimonial.rating) {
                            Icon(
                                Icons.Default.StarOutline,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                testimonial.comment,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppTopBar(cartItemCount: Int) {
//    CenterAlignedTopAppBar(
//        title = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
////                Image(
////                    painter = painterResource(id = R.drawable.logo),
////                    contentDescription = "App Logo",
////                    modifier = Modifier
////                        .size(40.dp)
////                        .padding(end = 1.dp),
////                    contentScale = ContentScale.Fit
////                )
//
//                Text(
//                    text = "PrettyNBeauty",
//                    style = MaterialTheme.typography.titleLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = Pink80
//                )
//            }
//        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = Color.White,
//            scrolledContainerColor = Color.White
//        ),
//        actions = {
//            Box(contentAlignment = Alignment.Center) {
//                // icon
//            }
//        }
//    )
//}