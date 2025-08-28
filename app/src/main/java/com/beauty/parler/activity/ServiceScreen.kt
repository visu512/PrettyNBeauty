//package com.beauty.parler.activity
//
//import android.app.DatePickerDialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.LocalOffer
//import androidx.compose.material.icons.outlined.Star
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.beauty.parler.model.BeautyPackage
//import com.beauty.parler.model.CartItem
//import com.beauty.parler.model.Offer
//import com.beauty.parler.model.PackageViewModel
//import com.beauty.parler.model.Service
//import com.beauty.parler.shared.CartViewModel
//import com.beauty.parler.ui.theme.*
//import com.google.firebase.Firebase
//import com.google.firebase.Timestamp
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.tasks.await
//import java.text.SimpleDateFormat
//import java.util.*
//
//enum class ContentType {
//    SERVICES, PACKAGES
//}
//
//enum class ServiceCategory(val id: String, val displayName: String, val imageUrl: String = "") {
//    PACKAGES("packages", "Packages", "https://example.com/packages.jpg"),
//    FACIALS("facials", "Facials", "https://i.postimg.cc/Pr1jsf7H/Whats-App-Image-2025-08-06-at-11-34-51-18ae752e.jpg"),
//    DETAN("detan", "De-Tan", "https://i.postimg.cc/Pr1jsf7H/Whats-App-Image-2025-08-06-at-11-34-51-18ae752e.jpg"),
//    BLEACH("bleach", "Bleach", "https://example.com/bleach.jpg"),
//    WAXING("waxing", "Waxing", "https://example.com/waxing.jpg"),
//    MANICURE("manicure","Manicure", "https://example.com/manicure.jpg"),
//    PEDICURE("pedicure", "Pedicure", "https://example.com/pedicure.jpg"),
//    HAIR_CARE("hair_care", "Hair Care", "https://example.com/haircare.jpg"),
//    MASSAGE("massage", "Massage", "https://example.com/massage.jpg"),
//    THREADING("threading", "Threading", "https://example.com/threading.jpg"),
//    MAKEUP("makeup", "Makeup", "https://example.com/makeup.jpg"),
//    SPECIAL_TREATMENTS("special_treatments", "Special Treatments", "https://example.com/special.jpg"),
//    BRIDAL("bridal", "Bridal Services", "https://example.com/bridal.jpg");
//
//    companion object {
//        fun fromId(id: String): ServiceCategory? {
//            return values().find { it.id == id }
//        }
//    }
//}
//
//data class BookingDetails(
//    val packageName: String,
//    val selectedServices: List<String>,
//    val customerName: String,
//    val phoneNumber: String,
//    val address: String,
//    val date: String,
//    val time: String,
//    val numberOfPersons: Int,
//    val totalPrice: Double,
//    val totalDuration: Int,
//    val appliedCoupon: Offer?
//)
//
//@OptIn(
//    ExperimentalMaterial3Api::class,
//    ExperimentalFoundationApi::class,
//    ExperimentalAnimationApi::class
//)
//
//@Composable
//fun ServiceScreen(
//    navController: NavController,
//    cartViewModel: CartViewModel,
//    packageViewModel: PackageViewModel
//) {
//    var selectedContent by remember { mutableStateOf(ContentType.SERVICES) }
//    var selectedCategory by remember { mutableStateOf<ServiceCategory?>(null) }
//    var selectedItem by remember { mutableStateOf<Any?>(null) }
//    var showRatingDialog by remember { mutableStateOf(false) }
//    var userRating by remember { mutableStateOf(0) }
//    var showCouponDialog by remember { mutableStateOf(false) }
//    var appliedCoupon by remember { mutableStateOf<Offer?>(null) }
//    var showBookingSuccessDialog by remember { mutableStateOf(false) }
//    var bookingDetails by remember { mutableStateOf<BookingDetails?>(null) }
//
//    // NEW: Add these state variables for service selection and booking
//    var showServiceSelection by remember { mutableStateOf(false) }
//    var selectedServices by remember { mutableStateOf<List<String>>(emptyList()) }
//    var showBookingDialog by remember { mutableStateOf(false) }
//
//    var services by remember { mutableStateOf<List<Service>>(emptyList()) }
//    var packages by remember { mutableStateOf<List<BeautyPackage>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    var availableOffers by remember { mutableStateOf<List<Offer>>(emptyList()) }
//
//    val db = Firebase.firestore
//    val context = LocalContext.current
//
//    // Load data from Firestore
//    LaunchedEffect(selectedContent) {
//        isLoading = true
//        errorMessage = null
//
//        try {
//            // Load offers
//            val offersResult = db.collection("offers").get().await()
//            availableOffers = offersResult.documents.mapNotNull { doc ->
//                try {
//                    Offer(
//                        id = doc.id,
//                        name = doc.getString("name") ?: "Special Offer",
//                        title = doc.getString("title") ?: "Discount",
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
//                } catch (e: Exception) {
//                    null
//                }
//            }
//
//            // Load services
//            val servicesResult = db.collection("services").get().await()
//            services = servicesResult.documents.mapNotNull { doc ->
//                try {
//                    Service(
//                        id = doc.id,
//                        name = doc.getString("name") ?: "",
//                        description = doc.getString("description") ?: "",
//                        price = doc.getString("price")?.toDoubleOrNull() ?: 0.0,
//                        duration = doc.getString("time")?.toIntOrNull() ?: 30,
//                        rating = doc.getDouble("rating")?.toFloat() ?: 4.8f,
//                        imageUrl = doc.getString("imageUrl") ?: "",
//                        categoryId = doc.getString("categoryId") ?: "",
//                        categoryName = doc.getString("categoryName") ?: ""
//                    )
//                } catch (e: Exception) {
//                    null
//                }
//            }
//
//            // Load packages
//            val packagesResult = db.collection("packages").get().await()
//            packages = packagesResult.documents.mapNotNull { doc ->
//                try {
//                    val serviceNames = (doc.get("serviceNames") as? List<String>) ?: emptyList()
//                    val servicePrices = (doc.get("services") as? List<String>)?.map { it.toDoubleOrNull() ?: 0.0 }
//                        ?: emptyList()
//
//                    val servicesDetails = (doc.get("servicesDetails") as? List<Map<String, Any>>)?.map { detail ->
//                        Service(
//                            id = detail["id"] as? String ?: "",
//                            name = detail["name"] as? String ?: "",
//                            description = detail["description"] as? String ?: "",
//                            price = (detail["price"] as? Number)?.toDouble() ?: 0.0,
//                            duration = (detail["duration"] as? Number)?.toInt() ?: 0,
//                            rating = 4.8f,
//                            imageUrl = detail["imageUrl"] as? String ?: "",
//                            categoryId = detail["categoryId"] as? String ?: "",
//                            categoryName = detail["categoryName"] as? String ?: ""
//                        )
//                    } ?: emptyList()
//
//                    BeautyPackage(
//                        id = doc.id,
//                        name = doc.getString("name") ?: "",
//                        description = doc.getString("description") ?: "",
//                        price = doc.getString("price")?.toDoubleOrNull() ?: 0.0,
//                        servicesIncluded = serviceNames,
//                        serviceImageUrls = emptyList(),
//                        servicePrices = servicePrices,
//                        rating = doc.getDouble("rating")?.toFloat() ?: 4.7f,
//                        imageUrl = doc.getString("packageImageUrl") ?: "",
//                        servicesDetails = servicesDetails,
//                        durations = doc.getString("duration") ?: servicesDetails.sumOf { it.duration }.toString()
//                    )
//                } catch (e: Exception) {
//                    null
//                }
//            }
//
//        } catch (e: Exception) {
//            errorMessage = "Failed to load data: ${e.message}"
//            Log.e("ServiceScreen", "Error loading data", e)
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
//                        "Beauty Services & Packages",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.White
//                ),
//                actions = {
//                    if (appliedCoupon != null) {
//                        BadgedBox(
//                            badge = {
//                                Badge {
//                                    Text("${appliedCoupon?.discountPercentage}%", fontSize = 10.sp)
//                                }
//                            }
//                        ) {
//                            IconButton(onClick = { showCouponDialog = true }) {
//                                Icon(
//                                    Icons.Default.LocalOffer,
//                                    contentDescription = "Coupon",
//                                    tint = HotPink
//                                )
//                            }
//                        }
//                    } else if (availableOffers.isNotEmpty()) {
//                        IconButton(onClick = { showCouponDialog = true }) {
//                            Icon(
//                                Icons.Outlined.LocalOffer,
//                                contentDescription = "Offers",
//                                tint = HotPink
//                            )
//                        }
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
//            Column(modifier = Modifier.padding(innerPadding)) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    ContentTypeSelector(
//                        selected = selectedContent == ContentType.SERVICES,
//                        onClick = { selectedContent = ContentType.SERVICES },
//                        text = "Services",
//                        modifier = Modifier.weight(1f)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    ContentTypeSelector(
//                        selected = selectedContent == ContentType.PACKAGES,
//                        onClick = { selectedContent = ContentType.PACKAGES },
//                        text = "Packages",
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//
//                when {
//                    isLoading -> LoadingView()
//                    errorMessage != null -> ErrorView(errorMessage!!) {
//                        isLoading = true; errorMessage = null
//                    }
//
//                    selectedContent == ContentType.SERVICES -> {
//                        if (selectedCategory == null) {
//                            ServiceCategoriesView(
//                                onCategorySelected = { category -> selectedCategory = category }
//                            )
//                        } else {
//                            ServicesListView(
//                                services = services.filter { service ->
//                                    service.categoryName.equals(selectedCategory?.displayName, ignoreCase = true)
//                                },
//                                categoryName = selectedCategory?.displayName ?: "",
//                                onBackToCategories = { selectedCategory = null },
//                                onServiceSelected = { selectedItem = it },
//                                onRateService = { selectedItem = it; showRatingDialog = true },
//                                onAddToCart = { service ->
//                                    val discountPercentage = appliedCoupon?.discountPercentage ?: 0
//                                    val discountedPrice =
//                                        service.price * (100 - discountPercentage) / 100
//
//                                    cartViewModel.addToCart(
//                                        CartItem(
//                                            id = service.id,
//                                            productId = service.id,
//                                            name = service.name,
//                                            price = discountedPrice,
//                                            quantity = 1,
//                                            persons = 1,
//                                            imageUrl = service.imageUrl,
//                                            duration = "${service.duration} min",
//                                            dateTime = "To be scheduled",
//                                            originalPrice = service.price,
//                                            isPackage = false,
//                                            serviceDuration = service.duration
//                                        )
//                                    )
////                                    Toast.makeText(context, "${service.name} added to cart", Toast.LENGTH_SHORT).show()
//                                },
//                                appliedCoupon = appliedCoupon
//                            )
//                        }
//                    }
//
//                    selectedContent == ContentType.PACKAGES -> {
//                        PackagesListView(
//                            packages = packages,
//                            appliedCoupon = appliedCoupon,
//                            onAddToCart = { pkg ->
//                                if (cartViewModel.isItemInCart(pkg.id)) {
////                                    Toast.makeText(context, "${pkg.name} is already in cart", Toast.LENGTH_SHORT).show()
//                                    return@PackagesListView
//                                }
//
//                                cartViewModel.addToCart(
//                                    CartItem(
//                                        id = pkg.id,
//                                        productId = pkg.id,
//                                        name = pkg.name,
//                                        price = pkg.price,
//                                        quantity = 1,
//                                        persons = 1,
//                                        imageUrl = pkg.imageUrl,
//                                        duration = "${pkg.durations} min",
//                                        dateTime = "To be scheduled",
//                                        originalPrice = pkg.price,
//                                        isPackage = true,
//                                        packageServices = pkg.servicesIncluded,
//                                        serviceDuration = pkg.durations.toIntOrNull() ?: 0
//                                    )
//                                )
////                                Toast.makeText(context, "${pkg.name} added to cart", Toast.LENGTH_SHORT).show()
//                            },
//                            onBookPackage = { pkg ->
//                                selectedItem = pkg
//                                showServiceSelection = true
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        //  dialogs here at the end of Scaffold content
//        if (showServiceSelection && selectedItem is BeautyPackage) {
//            ServiceSelectionDialog(
//                services = (selectedItem as BeautyPackage).servicesIncluded,
//                onDismiss = { showServiceSelection = false },
//                onConfirm = { selected ->
//                    selectedServices = selected
//                    showServiceSelection = false
//                    showBookingDialog = true
//                }
//            )
//        }
//
//        if (showBookingDialog && selectedItem is BeautyPackage) {
//            PackageBookingDialog(
//                beautyPackage = selectedItem as BeautyPackage,
//                selectedServices = selectedServices,
//                appliedCoupon = appliedCoupon,
//                onDismiss = { showBookingDialog = false },
//                onBookingConfirmed = { details ->
//                    // Save to Firestore
//                    saveBookingToFirestore(details)
//                    bookingDetails = details
//                    showBookingDialog = false
//                    showBookingSuccessDialog = true
//                }
//            )
//        }
//
//        if (showRatingDialog && selectedItem != null) {
//            RatingDialog(
//                itemName = when (selectedItem) {
//                    is Service -> (selectedItem as Service).name
//                    is BeautyPackage -> (selectedItem as BeautyPackage).name
//                    else -> ""
//                },
//                currentRating = userRating,
//                onRatingChanged = { userRating = it },
//                onDismiss = { showRatingDialog = false },
//                onSubmit = { showRatingDialog = false }
//            )
//        }
//
//        if (showCouponDialog) {
//            CouponDialog(
//                offers = availableOffers,
//                appliedCoupon = appliedCoupon,
//                onDismiss = { showCouponDialog = false },
//                onApplyCoupon = { appliedCoupon = it; showCouponDialog = false },
//                onRemoveCoupon = { appliedCoupon = null; showCouponDialog = false }
//            )
//        }
//
//        if (showBookingSuccessDialog && bookingDetails != null) {
//            BookingSuccessDialog(
//                bookingDetails = bookingDetails!!,
//                onDismiss = { showBookingSuccessDialog = false }
//            )
//        }
//    }
//}
//
//@Composable
//private fun ServiceCategoriesView(
//    onCategorySelected: (ServiceCategory) -> Unit
//) {
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(bottom = 80.dp)
//    ) {
//        item {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    "Beauty Categories",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    "Choose a category to explore services",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Pink80
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//
//        items(ServiceCategory.values().filter { it != ServiceCategory.PACKAGES }) { category ->
//            ServiceCategoryCard(
//                category = category,
//                onCategorySelected = onCategorySelected
//            )
//        }
//    }
//}
//
//@Composable
//private fun ServiceCategoryCard(
//    category: ServiceCategory,
//    onCategorySelected: (ServiceCategory) -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 10.dp)
//            .fillMaxWidth()
//            .height(120.dp)
//            .clickable { onCategorySelected(category) },
//        shape = RoundedCornerShape(14.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            // Background image
//            if (category.imageUrl.isNotEmpty()) {
//                AsyncImage(
//                    model = category.imageUrl,
//                    contentDescription = category.displayName,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//                // Dark overlay for better text visibility
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
//                                startY = 0.5f
//                            )
//                        )
//                )
//            } else {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Pink10)
//                )
//            }
//
//            // Content
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .align(Alignment.BottomStart)
//            ) {
//                Text(
//                    text = category.displayName,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = if (category.imageUrl.isNotEmpty()) Color.White else HotPinkDark
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = "Browse services →",
//                    style = MaterialTheme.typography.labelSmall,
//                    color = if (category.imageUrl.isNotEmpty()) Color.White.copy(alpha = 0.9f) else HotPink,
//                    modifier = Modifier.align(Alignment.End)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun ServicesListView(
//    services: List<Service>,
//    categoryName: String,
//    onBackToCategories: () -> Unit,
//    onServiceSelected: (Service) -> Unit,
//    onRateService: (Service) -> Unit,
//    onAddToCart: (Service) -> Unit,
//    appliedCoupon: Offer?
//) {
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(bottom = 80.dp)
//    ) {
//        item {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(onClick = onBackToCategories) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = HotPink)
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        categoryName,
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//
//                if (appliedCoupon != null) {
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = CardDefaults.cardColors(containerColor = Pink10)
//                    ) {
//                        Row(
//                            modifier = Modifier.padding(8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                Icons.Default.LocalOffer,
//                                contentDescription = "Coupon",
//                                tint = HotPink,
//                                modifier = Modifier.size(20.dp)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Column {
//                                Text(
//                                    "Coupon: ${appliedCoupon.useCode}",
//                                    style = MaterialTheme.typography.bodySmall,
//                                    fontWeight = FontWeight.Bold,
//                                    color = HotPinkDark
//                                )
//                                Text(
//                                    "${appliedCoupon.discountPercentage}% discount",
//                                    style = MaterialTheme.typography.labelSmall,
//                                    color = HotPink
//                                )
//                            }
//                        }
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//
//        if (services.isEmpty()) {
//            item {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(32.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "No services found in this category",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = Pink80
//                    )
//                }
//            }
//        } else {
//            items(services) { service ->
//                ServiceCard(
//                    service = service,
//                    onCardClick = { onServiceSelected(service) },
//                    onRateClick = { onRateService(service) },
//                    onAddToCart = { onAddToCart(service) },
//                    appliedCoupon = appliedCoupon
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun ServiceCard(
//    service: Service,
//    onCardClick: () -> Unit,
//    onRateClick: () -> Unit,
//    onAddToCart: (Service) -> Unit,
//    appliedCoupon: Offer?
//) {
//    var isExpanded by remember { mutableStateOf(false) }
//    val discountedPrice = if (appliedCoupon != null) {
//        service.price * (100 - appliedCoupon.discountPercentage) / 100
//    } else {
//        service.price
//    }
//
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .clickable { isExpanded = !isExpanded },
//        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.animateContentSize()) {
//            Row(
//                modifier = Modifier.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Service Image
//                if (service.imageUrl.isNotEmpty()) {
//                    AsyncImage(
//                        model = service.imageUrl,
//                        contentDescription = service.name,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(100.dp)
//                            .clip(RoundedCornerShape(8.dp))
//                    )
//                } else {
//                    Box(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(Pink10)
//                    ) {
//                        Icon(
//                            Icons.Default.Spa,
//                            contentDescription = "Service",
//                            tint = HotPink,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .align(Alignment.Center)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        service.name,
//                        style = MaterialTheme.typography.titleSmall,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            Icons.Filled.Star,
//                            contentDescription = "Rating",
//                            tint = HotPink,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Text(
//                            "${service.rating}",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color.Gray,
//                            modifier = Modifier.padding(start = 4.dp)
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Column {
//                            if (appliedCoupon != null) {
//                                Text(
//                                    "₹${"%.0f".format(service.price)}",
//                                    style = MaterialTheme.typography.bodySmall,
//                                    color = Color.Gray,
//                                    textDecoration = TextDecoration.LineThrough
//                                )
//                            }
//                            Text(
//                                "₹${"%.0f".format(discountedPrice)}",
//                                style = MaterialTheme.typography.titleSmall,
//                                fontWeight = FontWeight.Bold,
//                                color = HotPink
//                            )
//                        }
//                        Text(
//                            "${service.duration} min",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color.Gray,
//                            modifier = Modifier
//                                .background(Pink20, RoundedCornerShape(4.dp))
//                                .padding(horizontal = 8.dp, vertical = 4.dp)
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
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        service.description,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray,
//                        lineHeight = 20.sp
//                    )
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Button(
//                        onClick = { onAddToCart(service) },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Add to Cart")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun PackagesListView(
//    packages: List<BeautyPackage>,
//    appliedCoupon: Offer?,
//    onAddToCart: (BeautyPackage) -> Unit,
//    onBookPackage: (BeautyPackage) -> Unit
//) {
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(bottom = 80.dp)
//    ) {
//        item {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    "Beauty Packages",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    "Special packages at discounted rates",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Pink80
//                )
//                if (appliedCoupon != null) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Card(
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = CardDefaults.cardColors(containerColor = Pink10)
//                    ) {
//                        Row(
//                            modifier = Modifier.padding(8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                Icons.Default.LocalOffer,
//                                contentDescription = "Coupon",
//                                tint = HotPink,
//                                modifier = Modifier.size(20.dp)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Column {
//                                Text(
//                                    "Coupon: ${appliedCoupon.useCode}",
//                                    style = MaterialTheme.typography.bodySmall,
//                                    fontWeight = FontWeight.Bold,
//                                    color = HotPinkDark
//                                )
//                                Text(
//                                    "${appliedCoupon.discountPercentage}% discount",
//                                    style = MaterialTheme.typography.labelSmall,
//                                    color = HotPink
//                                )
//                            }
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
//
//        if (packages.isEmpty()) {
//            item {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(32.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "No packages available",
//                        style = MaterialTheme.typography.bodyLarge,
//                        color = Pink80
//                    )
//                }
//            }
//        } else {
//            items(packages) { beautyPackage ->
//                PackageCard(
//                    beautyPackage = beautyPackage,
//                    appliedCoupon = appliedCoupon,
//                    onAddToCart = { onAddToCart(beautyPackage) },
//                    onBookPackage = { onBookPackage(beautyPackage) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun PackageCard(
//    beautyPackage: BeautyPackage,
//    appliedCoupon: Offer?,
//    onAddToCart: (BeautyPackage) -> Unit,
//    onBookPackage: (BeautyPackage) -> Unit
//) {
//    var isExpanded by remember { mutableStateOf(false) }
//
//    val discountedPrice = if (appliedCoupon != null) {
//        beautyPackage.price * (100 - appliedCoupon.discountPercentage) / 100
//    } else {
//        beautyPackage.price
//    }
//
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .clickable { isExpanded = !isExpanded },
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.animateContentSize()) {
//            // Package Image
//            if (beautyPackage.imageUrl.isNotEmpty()) {
//                AsyncImage(
//                    model = beautyPackage.imageUrl,
//                    contentDescription = beautyPackage.name,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(120.dp)
//                )
//            } else {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(120.dp)
//                        .background(Pink10),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        Icons.Default.Spa,
//                        contentDescription = "Package",
//                        tint = HotPink,
//                        modifier = Modifier.size(60.dp)
//                    )
//                }
//            }
//
//            Column(modifier = Modifier.padding(16.dp)) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        beautyPackage.name,
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark,
//                        modifier = Modifier.weight(1f)
//                    )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(start = 8.dp)
//                    ) {
//                        Icon(
//                            Icons.Filled.Star,
//                            contentDescription = null,
//                            tint = HotPink,
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Text(
//                            "${beautyPackage.rating}",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color.Gray,
//                            modifier = Modifier.padding(start = 4.dp)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Column {
//                        if (appliedCoupon != null) {
//                            Text(
//                                "₹${"%.0f".format(beautyPackage.price)}",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = Color.Gray,
//                                textDecoration = TextDecoration.LineThrough
//                            )
//                        }
//                        Text(
//                            "₹${"%.0f".format(discountedPrice)}",
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPink
//                        )
//                    }
//                    Text(
//                        "${beautyPackage.durations} min",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray,
//                        modifier = Modifier
//                            .background(Pink20, RoundedCornerShape(4.dp))
//                            .padding(horizontal = 8.dp, vertical = 4.dp)
//                    )
//                }
//
//                if (isExpanded) {
//                    Divider(
//                        modifier = Modifier.padding(vertical = 8.dp),
//                        color = Pink20,
//                        thickness = 1.dp
//                    )
//                    Column {
//                        Text(
//                            beautyPackage.description,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color.Gray,
//                            lineHeight = 20.sp
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Text(
//                            "Included Services:",
//                            style = MaterialTheme.typography.bodyMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPinkDark
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        beautyPackage.servicesIncluded.forEachIndexed { index, service ->
//                            Text(
//                                "• $service",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = Color.Gray,
//                                modifier = Modifier.padding(vertical = 2.dp)
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Button(
//                                onClick = { onBookPackage(beautyPackage) },
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = HotPink,
//                                    contentColor = Color.White
//                                ),
//                                shape = RoundedCornerShape(12.dp),
//                                modifier = Modifier.weight(1f)
//                            ) {
//                                Text("Book Now")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ServiceSelectionDialog(
//    services: List<String>,
//    onDismiss: () -> Unit,
//    onConfirm: (List<String>) -> Unit
//) {
//    var selectedServices by remember { mutableStateOf(emptySet<String>()) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Select Services",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                Text(
//                    "Choose up to 4 services:",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//
//                services.forEach { service ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                            .clickable {
//                                selectedServices = if (selectedServices.contains(service)) {
//                                    selectedServices - service
//                                } else if (selectedServices.size < 4) {
//                                    selectedServices + service
//                                } else {
//                                    selectedServices
//                                }
//                            }
//                    ) {
//                        Checkbox(
//                            checked = selectedServices.contains(service),
//                            onCheckedChange = { checked ->
//                                selectedServices = if (checked && selectedServices.size < 4) {
//                                    selectedServices + service
//                                } else if (!checked) {
//                                    selectedServices - service
//                                } else {
//                                    selectedServices
//                                }
//                            }
//                        )
//                        Text(
//                            service,
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(start = 8.dp)
//                        )
//                    }
//                }
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = { onConfirm(selectedServices.toList()) },
//                enabled = selectedServices.isNotEmpty(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = HotPink,
//                    contentColor = Color.White
//                )
//            ) {
//                Text("Confirm (${selectedServices.size}/4)")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel", color = HotPink)
//            }
//        }
//    )
//}
//
//@Composable
//fun PackageBookingDialog(
//    beautyPackage: BeautyPackage,
//    selectedServices: List<String>,
//    appliedCoupon: Offer?,
//    onDismiss: () -> Unit,
//    onBookingConfirmed: (BookingDetails) -> Unit
//) {
//    var customerName by remember { mutableStateOf("") }
//    var phoneNumber by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var showDatePicker by remember { mutableStateOf(false) }
//    var showTimePicker by remember { mutableStateOf(false) }
//    var selectedDate by remember { mutableStateOf("") }
//    var selectedTime by remember { mutableStateOf("") }
//    var numberOfPersons by remember { mutableStateOf(1) }
//
//    val context = LocalContext.current
//
//    val discountedPrice = if (appliedCoupon != null) {
//        beautyPackage.price * (100 - appliedCoupon.discountPercentage) / 100
//    } else {
//        beautyPackage.price
//    }
//
//    val totalDuration = beautyPackage.durations.toIntOrNull() ?: 0
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Book ${beautyPackage.name}",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column(
//                modifier = Modifier
//                    .verticalScroll(rememberScrollState())
//                    .background(Color.White, RoundedCornerShape(8.dp))
//            ) {
//                Text(
//                    "Selected Services:",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                selectedServices.forEach { service ->
//                    Text(
//                        "• $service",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Gray
//                    )
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//
//                OutlinedTextField(
//                    value = customerName,
//                    onValueChange = { customerName = it },
//                    label = { Text("Customer Name") },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = HotPink,
//                        focusedLabelColor = HotPink
//                    )
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//                OutlinedTextField(
//                    value = phoneNumber,
//                    onValueChange = { phoneNumber = it },
//                    label = { Text("Phone Number") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = HotPink,
//                        focusedLabelColor = HotPink
//                    )
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//                OutlinedTextField(
//                    value = address,
//                    onValueChange = { address = it },
//                    label = { Text("Address") },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = HotPink,
//                        focusedLabelColor = HotPink
//                    )
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Date and Time Selection
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    // Date field with manual input and picker
//                    Column(modifier = Modifier.weight(1f)) {
//                        OutlinedTextField(
//                            value = selectedDate,
//                            onValueChange = { selectedDate = it },
//                            label = { Text("Date") },
//                            placeholder = { Text("DD/MM/YYYY") },
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                            trailingIcon = {
//                                Icon(
//                                    Icons.Default.CalendarToday,
//                                    contentDescription = "Select Date",
//                                    tint = HotPink,
//                                    modifier = Modifier.clickable { showDatePicker = true }
//                                )
//                            },
//                            modifier = Modifier.fillMaxWidth(),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedBorderColor = HotPink,
//                                focusedLabelColor = HotPink
//                            )
//                        )
//                        Text(
//                            "Format: DD/MM/YYYY",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = Color.Gray,
//                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    // Time field with manual input and picker
//                    Column(modifier = Modifier.weight(1f)) {
//                        OutlinedTextField(
//                            value = selectedTime,
//                            onValueChange = { selectedTime = it },
//                            label = { Text("Time") },
//                            placeholder = { Text("HH:MM AM/PM") },
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                            trailingIcon = {
//                                Icon(
//                                    Icons.Default.Schedule,
//                                    contentDescription = "Select Time",
//                                    tint = HotPink,
//                                    modifier = Modifier.clickable { showTimePicker = true }
//                                )
//                            },
//                            modifier = Modifier.fillMaxWidth(),
//                            colors = OutlinedTextFieldDefaults.colors(
//                                focusedBorderColor = HotPink,
//                                focusedLabelColor = HotPink
//                            )
//                        )
//                        Text(
//                            "Format: HH:MM AM/PM",
//                            style = MaterialTheme.typography.labelSmall,
//                            color = Color.Gray,
//                            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text("Number of Persons: ", style = MaterialTheme.typography.bodyMedium)
//                    Spacer(modifier = Modifier.width(8.dp))
//                    IconButton(onClick = { if (numberOfPersons > 1) numberOfPersons-- }) {
//                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
//                    }
//                    Text("$numberOfPersons", style = MaterialTheme.typography.bodyMedium)
//                    IconButton(onClick = { numberOfPersons++ }) {
//                        Icon(Icons.Default.Add, contentDescription = "Increase")
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    "Total Duration: ${totalDuration} minutes",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = HotPinkDark
//                )
//                Text(
//                    "Total: ₹${"%.0f".format(discountedPrice * numberOfPersons)}",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPink
//                )
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
//                        address.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()
//                    ) {
//                        // Validate date format
//                        if (!isValidDateFormat(selectedDate)) {
//                            Toast.makeText(context, "Please enter date in DD/MM/YYYY format", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//
//                        // Validate time format
//                        if (!isValidTimeFormat(selectedTime)) {
//                            Toast.makeText(context, "Please enter time in HH:MM AM/PM format", Toast.LENGTH_SHORT).show()
//                            return@Button
//                        }
//
//                        val totalPrice = discountedPrice * numberOfPersons
//
//                        onBookingConfirmed(
//                            BookingDetails(
//                                packageName = beautyPackage.name,
//                                selectedServices = selectedServices,
//                                customerName = customerName,
//                                phoneNumber = phoneNumber,
//                                address = address,
//                                date = selectedDate,
//                                time = selectedTime,
//                                numberOfPersons = numberOfPersons,
//                                totalPrice = totalPrice,
//                                totalDuration = totalDuration,
//                                appliedCoupon = appliedCoupon
//                            )
//                        )
//                    } else {
//                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = HotPink,
//                    contentColor = Color.White
//                ),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Text("Confirm Booking")
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = onDismiss,
//                colors = ButtonDefaults.textButtonColors(contentColor = HotPink)
//            ) {
//                Text("Cancel")
//            }
//        },
//        containerColor = Color.White,
//        shape = RoundedCornerShape(20.dp)
//    )
//
//    if (showDatePicker) {
//        ShowDatePickerDialog(
//            onDateSelected = { date ->
//                selectedDate = date
//                showDatePicker = false
//            },
//            onDismiss = { showDatePicker = false }
//        )
//    }
//
//    if (showTimePicker) {
//        ShowTimePickerDialog(
//            onTimeSelected = { time ->
//                selectedTime = time
//                showTimePicker = false
//            },
//            onDismiss = { showTimePicker = false }
//        )
//    }
//}
//
//// Helper function to validate date format
//fun isValidDateFormat(date: String): Boolean {
//    return try {
//        val parts = date.split("/")
//        if (parts.size != 3) return false
//
//        val day = parts[0].toInt()
//        val month = parts[1].toInt()
//        val year = parts[2].toInt()
//
//        day in 1..31 && month in 1..12 && year >= Calendar.getInstance().get(Calendar.YEAR)
//    } catch (e: Exception) {
//        false
//    }
//}
//
//// Helper function to validate time format
//fun isValidTimeFormat(time: String): Boolean {
//    return try {
//        val timePattern = Regex("^(0?[1-9]|1[0-2]):[0-5][0-9]\\s?(AM|PM|am|pm)\$")
//        timePattern.matches(time)
//    } catch (e: Exception) {
//        false
//    }
//}
//
//@Composable
//fun ShowDatePickerDialog(onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//
//    DisposableEffect(Unit) {
//        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//            calendar.set(Calendar.YEAR, year)
//            calendar.set(Calendar.MONTH, month)
//            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            onDateSelected(sdf.format(calendar.time))
//        }
//
//        val dialog = DatePickerDialog(
//            context,
//            dateSetListener,
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).apply {
//            datePicker.minDate = System.currentTimeMillis() - 1000
//            setOnDismissListener { onDismiss() }
//            show()
//        }
//
//        onDispose {
//            dialog.dismiss()
//        }
//    }
//}
//
//@Composable
//fun ShowTimePickerDialog(onTimeSelected: (String) -> Unit, onDismiss: () -> Unit) {
//    val context = LocalContext.current
//    val calendar = Calendar.getInstance()
//
//    DisposableEffect(Unit) {
//        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//            calendar.set(Calendar.MINUTE, minute)
//
//            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//            onTimeSelected(sdf.format(calendar.time))
//        }
//
//        val dialog = TimePickerDialog(
//            context,
//            timeSetListener,
//            calendar.get(Calendar.HOUR_OF_DAY),
//            calendar.get(Calendar.MINUTE),
//            false
//        ).apply {
//            setOnDismissListener { onDismiss() }
//            show()
//        }
//
//        onDispose {
//            dialog.dismiss()
//        }
//    }
//}
//
//@Composable
//fun BookingSuccessDialog(
//    bookingDetails: BookingDetails,
//    onDismiss: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Booking Confirmed!",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column {
//                Text(
//                    "Your package has been successfully booked.",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//                Text(
//                    "Package: ${bookingDetails.packageName}",
//                    style = MaterialTheme.typography.bodySmall,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    "Date: ${bookingDetails.date} at ${bookingDetails.time}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(
//                    "Total: ₹${"%.0f".format(bookingDetails.totalPrice)}",
//                    style = MaterialTheme.typography.bodySmall,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPink
//                )
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = onDismiss,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = HotPink,
//                    contentColor = Color.White
//                )
//            ) {
//                Text("OK")
//            }
//        }
//    )
//}
//
//private fun saveBookingToFirestore(bookingDetails: BookingDetails) {
//    val db = Firebase.firestore
//    val bookingData = hashMapOf(
//        "packageName" to bookingDetails.packageName,
//        "selectedServices" to bookingDetails.selectedServices,
//        "customerName" to bookingDetails.customerName,
//        "phoneNumber" to bookingDetails.phoneNumber,
//        "address" to bookingDetails.address,
//        "date" to bookingDetails.date,
//        "time" to bookingTimeTo24HourFormat(bookingDetails.time),
//        "numberOfPersons" to bookingDetails.numberOfPersons,
//        "totalPrice" to bookingDetails.totalPrice,
//        "totalDuration" to bookingDetails.totalDuration,
//        "appliedCoupon" to bookingDetails.appliedCoupon?.useCode,
//        "bookingDate" to Timestamp.now()
//    )
//
//    db.collection("PackageOrders")
//        .add(bookingData)
//        .addOnSuccessListener {
//            Log.d("ServiceScreen", "Booking saved successfully")
//        }
//        .addOnFailureListener { e ->
//            Log.e("ServiceScreen", "Error saving booking", e)
//        }
//}
//
//// Helper function to convert AM/PM time to 24-hour format for storage
//private fun bookingTimeTo24HourFormat(time: String): String {
//    return try {
//        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        val date = sdf.parse(time)
//        val militaryFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        militaryFormat.format(date)
//    } catch (e: Exception) {
//        time // Return original if conversion fails
//    }
//}
//
//@Composable
//fun CouponDialog(
//    offers: List<Offer>,
//    appliedCoupon: Offer?,
//    onDismiss: () -> Unit,
//    onApplyCoupon: (Offer) -> Unit,
//    onRemoveCoupon: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                if (appliedCoupon != null) "Applied Coupon" else "Available Coupons",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                if (appliedCoupon != null) {
//                    CouponItem(
//                        offer = appliedCoupon,
//                        isApplied = true,
//                        onApply = {},
//                        onRemove = onRemoveCoupon
//                    )
//                    Divider(modifier = Modifier.padding(vertical = 8.dp))
//                }
//                if (offers.isNotEmpty()) {
//                    Text(
//                        "Available Coupons:",
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    )
//                    offers.filter { it.id != appliedCoupon?.id }.forEach { offer ->
//                        CouponItem(
//                            offer = offer,
//                            isApplied = false,
//                            onApply = { onApplyCoupon(offer) },
//                            onRemove = {})
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//                } else {
//                    Text("No coupons available", style = MaterialTheme.typography.bodyMedium)
//                }
//            }
//        },
//        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
//    )
//}
//
//@Composable
//fun CouponItem(offer: Offer, isApplied: Boolean, onApply: () -> Unit, onRemove: () -> Unit) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = if (isApplied) Pink10 else Color.White),
//        border = BorderStroke(1.dp, if (isApplied) HotPink else Pink40)
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        offer.title,
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                    Text(
//                        offer.name,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Pink80
//                    )
//                }
//                if (isApplied) {
//                    Button(
//                        onClick = onRemove,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        modifier = Modifier.height(36.dp)
//                    ) {
//                        Text("Remove")
//                    }
//                } else {
//                    Button(
//                        onClick = onApply,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        modifier = Modifier.height(36.dp)
//                    ) {
//                        Text("Apply")
//                    }
//                }
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                offer.description,
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Gray
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    "Use code: ${offer.useCode}",
//                    style = MaterialTheme.typography.bodySmall,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPink
//                )
//                Text(
//                    "${offer.discountPercentage}% OFF",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPink
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ErrorView(errorMessage: String, onRetry: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            errorMessage,
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        Button(
//            onClick = onRetry,
//            colors = ButtonDefaults.buttonColors(
//                containerColor = HotPink,
//                contentColor = Color.White
//            )
//        ) {
//            Text("Retry")
//        }
//    }
//}
//
//@Composable
//fun LoadingView() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp), contentAlignment = Alignment.Center
//    ) {
//        Text("Loading...", style = MaterialTheme.typography.bodyMedium)
//    }
//}
//
//@Composable
//fun RatingDialog(
//    itemName: String,
//    currentRating: Int,
//    onRatingChanged: (Int) -> Unit,
//    onDismiss: () -> Unit,
//    onSubmit: () -> Unit
//) {
//    var rating by remember { mutableStateOf(currentRating) }
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Rate $itemName",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column {
//                Text(
//                    "How would you rate this service?",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    for (i in 1..5) {
//                        Icon(
//                            imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
//                            contentDescription = "$i star",
//                            tint = HotPink,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clickable { rating = i }
//                        )
//                    }
//                }
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = { onRatingChanged(rating); onSubmit() },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = HotPink,
//                    contentColor = Color.White
//                )
//            ) {
//                Text("Submit")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel", color = HotPink)
//            }
//        }
//    )
//}
//
//@Composable
//fun ContentTypeSelector(
//    selected: Boolean,
//    onClick: () -> Unit,
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    Surface(
//        modifier = modifier,
//        shape = RoundedCornerShape(12.dp),
//        color = if (selected) HotPink else Pink10,
//        border = BorderStroke(1.dp, if (selected) HotPink else Pink40),
//        onClick = onClick
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.bodyMedium,
//            color = if (selected) Color.White else HotPinkDark,
//            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
//            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
//        )
//    }
//}
//
//
//
//
//
//

















package com.beauty.parler.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beauty.parler.model.BeautyPackage
import com.beauty.parler.model.CartItem
import com.beauty.parler.model.Offer
import com.beauty.parler.model.PackageViewModel
import com.beauty.parler.model.Service
import com.beauty.parler.shared.CartViewModel
import com.beauty.parler.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

enum class ContentType {
    SERVICES, PACKAGES
}

enum class ServiceCategory(val id: String, val displayName: String, val imageUrl: String = "") {
    PACKAGES("packages", "Packages", "https://example.com/packages.jpg"),
    FACIALS("facials", "Facials", "https://i.postimg.cc/Pr1jsf7H/Whats-App-Image-2025-08-06-at-11-34-51-18ae752e.jpg"),
    DETAN("detan", "De-Tan", "https://i.postimg.cc/Pr1jsf7H/Whats-App-Image-2025-08-06-at-11-34-51-18ae752e.jpg"),
    BLEACH("bleach", "Bleach", "https://example.com/bleach.jpg"),
    WAXING("waxing", "Waxing", "https://example.com/waxing.jpg"),
    MANICURE("manicure","Manicure", "https://example.com/manicure.jpg"),
    PEDICURE("pedicure", "Pedicure", "https://example.com/pedicure.jpg"),
    HAIR_CARE("hair_care", "Hair Care", "https://example.com/haircare.jpg"),
    MASSAGE("massage", "Massage", "https://example.com/massage.jpg"),
    THREADING("threading", "Threading", "https://example.com/threading.jpg"),
    MAKEUP("makeup", "Makeup", "https://example.com/makeup.jpg"),
    SPECIAL_TREATMENTS("special_treatments", "Special Treatments", "https://example.com/special.jpg"),
    BRIDAL("bridal", "Bridal Services", "https://example.com/bridal.jpg");

    companion object {
        fun fromId(id: String): ServiceCategory? {
            return values().find { it.id == id }
        }
    }
}

data class BookingDetails(
    val packageName: String,
    val selectedServices: List<String>,
    val customerName: String,
    val phoneNumber: String,
    val address: String,
    val date: String,
    val time: String,
    val numberOfPersons: Int,
    val totalPrice: Double,
//    totalDuration: Int,
    val appliedCoupon: Offer?
)

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)

@Composable
fun ServiceScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    packageViewModel: PackageViewModel
) {
    var selectedContent by remember { mutableStateOf(ContentType.SERVICES) }
    var selectedCategory by remember { mutableStateOf<ServiceCategory?>(null) }
    var selectedItem by remember { mutableStateOf<Any?>(null) }
    var showRatingDialog by remember { mutableStateOf(false) }
    var userRating by remember { mutableStateOf(0) }
    var showCouponDialog by remember { mutableStateOf(false) }
    var appliedCoupon by remember { mutableStateOf<Offer?>(null) }
    var showBookingSuccessDialog by remember { mutableStateOf(false) }
    var bookingDetails by remember { mutableStateOf<BookingDetails?>(null) }

    // State variables for service selection and booking
    var showServiceSelection by remember { mutableStateOf(false) }
    var selectedServices by remember { mutableStateOf<List<String>>(emptyList()) }
    var showBookingDialog by remember { mutableStateOf(false) }

    var services by remember { mutableStateOf<List<Service>>(emptyList()) }
    var packages by remember { mutableStateOf<List<BeautyPackage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var availableOffers by remember { mutableStateOf<List<Offer>>(emptyList()) }

    val db = Firebase.firestore
    val context = LocalContext.current

    // Load data from Firestore
    LaunchedEffect(selectedContent) {
        isLoading = true
        errorMessage = null

        try {
            // Load offers
            val offersResult = db.collection("offers").get().await()
            availableOffers = offersResult.documents.mapNotNull { doc ->
                try {
                    Offer(
                        id = doc.id,
                        name = doc.getString("name") ?: "Special Offer",
                        title = doc.getString("title") ?: "Discount",
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
                } catch (e: Exception) {
                    null
                }
            }

            // Load services
            val servicesResult = db.collection("services").get().await()
            services = servicesResult.documents.mapNotNull { doc ->
                try {
                    Service(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        price = doc.getString("price")?.toDoubleOrNull() ?: 0.0,
                        duration = doc.getString("time")?.toIntOrNull() ?: 30,
                        rating = doc.getDouble("rating")?.toFloat() ?: 4.8f,
                        imageUrl = doc.getString("imageUrl") ?: "",
                        categoryId = doc.getString("categoryId") ?: "",
                        categoryName = doc.getString("categoryName") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }

            // Load packages
            val packagesResult = db.collection("packages").get().await()
            packages = packagesResult.documents.mapNotNull { doc ->
                try {
                    val serviceNames = (doc.get("serviceNames") as? List<String>) ?: emptyList()
                    val servicePrices = (doc.get("services") as? List<String>)?.map { it.toDoubleOrNull() ?: 0.0 }
                        ?: emptyList()

                    val servicesDetails = (doc.get("servicesDetails") as? List<Map<String, Any>>)?.map { detail ->
                        Service(
                            id = detail["id"] as? String ?: "",
                            name = detail["name"] as? String ?: "",
                            description = detail["description"] as? String ?: "",
                            price = (detail["price"] as? Number)?.toDouble() ?: 0.0,
                            duration = (detail["duration"] as? Number)?.toInt() ?: 0,
                            rating = 4.8f,
                            imageUrl = detail["imageUrl"] as? String ?: "",
                            categoryId = detail["categoryId"] as? String ?: "",
                            categoryName = detail["categoryName"] as? String ?: ""
                        )
                    } ?: emptyList()

                    BeautyPackage(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        price = doc.getString("price")?.toDoubleOrNull() ?: 0.0,
                        servicesIncluded = serviceNames,
                        serviceImageUrls = emptyList(),
                        servicePrices = servicePrices,
                        rating = doc.getDouble("rating")?.toFloat() ?: 4.7f,
                        imageUrl = doc.getString("packageImageUrl") ?: "",
                        servicesDetails = servicesDetails,
                        durations = doc.getString("duration") ?: servicesDetails.sumOf { it.duration }.toString()
                    )
                } catch (e: Exception) {
                    null
                }
            }

        } catch (e: Exception) {
            errorMessage = "Failed to load data: ${e.message}"
            Log.e("ServiceScreen", "Error loading data", e)
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Beauty Services & Packages",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                actions = {
                    if (appliedCoupon != null) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text("${appliedCoupon?.discountPercentage}%", fontSize = 10.sp)
                                }
                            }
                        ) {
                            IconButton(onClick = { showCouponDialog = true }) {
                                Icon(
                                    Icons.Default.LocalOffer,
                                    contentDescription = "Coupon",
                                    tint = HotPink
                                )
                            }
                        }
                    } else if (availableOffers.isNotEmpty()) {
                        IconButton(onClick = { showCouponDialog = true }) {
                            Icon(
                                Icons.Outlined.LocalOffer,
                                contentDescription = "Offers",
                                tint = HotPink
                            )
                        }
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

            Column(modifier = Modifier.padding(innerPadding)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ContentTypeSelector(
                        selected = selectedContent == ContentType.SERVICES,
                        onClick = { selectedContent = ContentType.SERVICES },
                        text = "Services",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ContentTypeSelector(
                        selected = selectedContent == ContentType.PACKAGES,
                        onClick = { selectedContent = ContentType.PACKAGES },
                        text = "Packages",
                        modifier = Modifier.weight(1f)
                    )
                }

                when {
                    isLoading -> LoadingView()
                    errorMessage != null -> ErrorView(errorMessage!!) {
                        isLoading = true; errorMessage = null
                    }

                    selectedContent == ContentType.SERVICES -> {
                        if (selectedCategory == null) {
                            ServiceCategoriesView(
                                onCategorySelected = { category -> selectedCategory = category }
                            )
                        } else {
                            ServicesListView(
                                services = services.filter { service ->
                                    service.categoryName.equals(selectedCategory?.displayName, ignoreCase = true)
                                },
                                categoryName = selectedCategory?.displayName ?: "",
                                onBackToCategories = { selectedCategory = null },
                                onServiceSelected = { selectedItem = it },
                                onRateService = { selectedItem = it; showRatingDialog = true },
                                onAddToCart = { service ->
                                    val discountPercentage = appliedCoupon?.discountPercentage ?: 0
                                    val discountedPrice =
                                        service.price * (100 - discountPercentage) / 100

                                    cartViewModel.addToCart(
                                        CartItem(
                                            id = service.id,
                                            productId = service.id,
                                            name = service.name,
                                            price = discountedPrice,
                                            quantity = 1,
                                            persons = 1,
                                            imageUrl = service.imageUrl,
                                            duration = "${service.duration} min",
                                            dateTime = "To be scheduled",
                                            originalPrice = service.price,
                                            isPackage = false,
                                            serviceDuration = service.duration
                                        )
                                    )
//                                    Toast.makeText(context, "${service.name} added to cart", Toast.LENGTH_SHORT).show()
                                },
                                appliedCoupon = appliedCoupon
                            )
                        }
                    }

                    selectedContent == ContentType.PACKAGES -> {
                        PackagesListView(
                            packages = packages,
                            appliedCoupon = appliedCoupon,
                            onAddToCart = { pkg ->
                                if (cartViewModel.isItemInCart(pkg.id)) {
//                                    Toast.makeText(context, "${pkg.name} is already in cart", Toast.LENGTH_SHORT).show()
                                    return@PackagesListView
                                }

                                cartViewModel.addToCart(
                                    CartItem(
                                        id = pkg.id,
                                        productId = pkg.id,
                                        name = pkg.name,
                                        price = pkg.price,
                                        quantity = 1,
                                        persons = 1,
                                        imageUrl = pkg.imageUrl,
                                        duration = "${pkg.durations} min",
                                        dateTime = "To be scheduled",
                                        originalPrice = pkg.price,
                                        isPackage = true,
                                        packageServices = pkg.servicesIncluded,
                                        serviceDuration = pkg.durations.toIntOrNull() ?: 0
                                    )
                                )
//                                Toast.makeText(context, "${pkg.name} added to cart", Toast.LENGTH_SHORT).show()
                            },
                            onBookPackage = { pkg ->
                                selectedItem = pkg
                                showServiceSelection = true
                            }
                        )
                    }
                }
            }
        }

        // Dialogs here at the end of Scaffold content
        if (showServiceSelection && selectedItem is BeautyPackage) {
            ServiceSelectionDialog(
                services = (selectedItem as BeautyPackage).servicesIncluded,
                onDismiss = { showServiceSelection = false },
                onConfirm = { selected ->
                    selectedServices = selected
                    showServiceSelection = false

                    // Add to cart directly after selection
                    val pkg = selectedItem as BeautyPackage
                    if (cartViewModel.isItemInCart(pkg.id)) {
//                        Toast.makeText(context, "${pkg.name} is already in cart", Toast.LENGTH_SHORT).show()
                        return@ServiceSelectionDialog
                    }

                    cartViewModel.addToCart(
                        CartItem(
                            id = pkg.id,
                            productId = pkg.id,
                            name = pkg.name,
                            price = pkg.price,
                            quantity = 1,
                            persons = 1,
                            imageUrl = pkg.imageUrl,
                            duration = "${pkg.durations} min",
                            dateTime = "To be scheduled",
                            originalPrice = pkg.price,
                            isPackage = true,
                            packageServices = pkg.servicesIncluded,
                            selectedServices = selectedServices.toString(),
                            serviceDuration = pkg.durations.toIntOrNull() ?: 0
                        )
                    )
//                    Toast.makeText(context, "${pkg.name} with ${selected.size} services added to cart", Toast.LENGTH_SHORT).show()
                }
            )
        }

        if (showRatingDialog && selectedItem != null) {
            RatingDialog(
                itemName = when (selectedItem) {
                    is Service -> (selectedItem as Service).name
                    is BeautyPackage -> (selectedItem as BeautyPackage).name
                    else -> ""
                },
                currentRating = userRating,
                onRatingChanged = { userRating = it },
                onDismiss = { showRatingDialog = false },
                onSubmit = { showRatingDialog = false }
            )
        }

        if (showCouponDialog) {
            CouponDialog(
                offers = availableOffers,
                appliedCoupon = appliedCoupon,
                onDismiss = { showCouponDialog = false },
                onApplyCoupon = { appliedCoupon = it; showCouponDialog = false },
                onRemoveCoupon = { appliedCoupon = null; showCouponDialog = false }
            )
        }

        if (showBookingSuccessDialog && bookingDetails != null) {
            BookingSuccessDialog(
                bookingDetails = bookingDetails!!,
                onDismiss = { showBookingSuccessDialog = false }
            )
        }
    }
}

@Composable
private fun ServiceCategoriesView(
    onCategorySelected: (ServiceCategory) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Beauty Categories",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Choose a category to explore services",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Pink80
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        items(ServiceCategory.values().filter { it != ServiceCategory.PACKAGES }) { category ->
            ServiceCategoryCard(
                category = category,
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
private fun ServiceCategoryCard(
    category: ServiceCategory,
    onCategorySelected: (ServiceCategory) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onCategorySelected(category) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            if (category.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = category.imageUrl,
                    contentDescription = category.displayName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Dark overlay for better text visibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                startY = 0.5f
                            )
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Pink10)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (category.imageUrl.isNotEmpty()) Color.White else HotPinkDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Browse services →",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (category.imageUrl.isNotEmpty()) Color.White.copy(alpha = 0.9f) else HotPink,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun ServicesListView(
    services: List<Service>,
    categoryName: String,
    onBackToCategories: () -> Unit,
    onServiceSelected: (Service) -> Unit,
    onRateService: (Service) -> Unit,
    onAddToCart: (Service) -> Unit,
    appliedCoupon: Offer?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackToCategories) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = HotPink)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        categoryName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (appliedCoupon != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Pink10)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocalOffer,
                                contentDescription = "Coupon",
                                tint = HotPink,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Coupon: ${appliedCoupon.useCode}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HotPinkDark
                                )
                                Text(
                                    "${appliedCoupon.discountPercentage}% discount",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HotPink
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (services.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No services found in this category",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Pink80
                    )
                }
            }
        } else {
            items(services) { service ->
                ServiceCard(
                    service = service,
                    onCardClick = { onServiceSelected(service) },
                    onRateClick = { onRateService(service) },
                    onAddToCart = { onAddToCart(service) },
                    appliedCoupon = appliedCoupon
                )
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: Service,
    onCardClick: () -> Unit,
    onRateClick: () -> Unit,
    onAddToCart: (Service) -> Unit,
    appliedCoupon: Offer?
) {
    var isExpanded by remember { mutableStateOf(false) }
    val discountedPrice = if (appliedCoupon != null) {
        service.price * (100 - appliedCoupon.discountPercentage) / 100
    } else {
        service.price
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Service Image
                if (service.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = service.imageUrl,
                        contentDescription = service.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Pink10)
                    ) {
                        Icon(
                            Icons.Default.Spa,
                            contentDescription = "Service",
                            tint = HotPink,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        service.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = HotPink,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "${service.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            if (appliedCoupon != null) {
                                Text(
                                    "₹${"%.0f".format(service.price)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Text(
                                "₹${"%.0f".format(discountedPrice)}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = HotPink
                            )
                        }
                        Text(
                            "${service.duration} min",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier
                                .background(Pink20, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        service.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onAddToCart(service) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add to Cart")
                    }
                }
            }
        }
    }
}

@Composable
private fun PackagesListView(
    packages: List<BeautyPackage>,
    appliedCoupon: Offer?,
    onAddToCart: (BeautyPackage) -> Unit,
    onBookPackage: (BeautyPackage) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Beauty Packages",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Special packages at discounted rates",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Pink80
                )
                if (appliedCoupon != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Pink10)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocalOffer,
                                contentDescription = "Coupon",
                                tint = HotPink,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    "Coupon: ${appliedCoupon.useCode}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = HotPinkDark
                                )
                                Text(
                                    "${appliedCoupon.discountPercentage}% discount",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = HotPink
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (packages.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No packages available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Pink80
                    )
                }
            }
        } else {
            items(packages) { beautyPackage ->
                PackageCard(
                    beautyPackage = beautyPackage,
                    appliedCoupon = appliedCoupon,
                    onAddToCart = { onAddToCart(beautyPackage) },
                    onBookPackage = { onBookPackage(beautyPackage) }
                )
            }
        }
    }
}

@Composable
fun PackageCard(
    beautyPackage: BeautyPackage,
    appliedCoupon: Offer?,
    onAddToCart: (BeautyPackage) -> Unit,
    onBookPackage: (BeautyPackage) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val discountedPrice = if (appliedCoupon != null) {
        beautyPackage.price * (100 - appliedCoupon.discountPercentage) / 100
    } else {
        beautyPackage.price
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            // Package Image
            if (beautyPackage.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = beautyPackage.imageUrl,
                    contentDescription = beautyPackage.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Pink10),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Spa,
                        contentDescription = "Package",
                        tint = HotPink,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        beautyPackage.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = HotPink,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "${beautyPackage.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        if (appliedCoupon != null) {
                            Text(
                                "₹${"%.0f".format(beautyPackage.price)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            "₹${"%.0f".format(discountedPrice)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HotPink
                        )
                    }
                    Text(
                        "${beautyPackage.durations} min",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier
                            .background(Pink20, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                if (isExpanded) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Pink20,
                        thickness = 1.dp
                    )
                    Column {
                        Text(
                            beautyPackage.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Included Services:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = HotPinkDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        beautyPackage.servicesIncluded.forEachIndexed { index, service ->
                            Text(
                                "• $service",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
//                            Button(
//                                onClick = { onAddToCart(beautyPackage) },
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Pink40,
//                                    contentColor = Color.White
//                                ),
//                                shape = RoundedCornerShape(12.dp),
//                                modifier = Modifier.weight(1f)
//                            ) {
//                                Text("Add to Cart")
//                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onBookPackage(beautyPackage) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = HotPink,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Select Services")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceSelectionDialog(
    services: List<String>,
    onDismiss: () -> Unit,
    onConfirm: (List<String>) -> Unit
) {
    var selectedServices by remember { mutableStateOf(emptySet<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Select Services",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    "Choose up to 4 services:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                services.forEach { service ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                selectedServices = if (selectedServices.contains(service)) {
                                    selectedServices - service
                                } else if (selectedServices.size < 4) {
                                    selectedServices + service
                                } else {
                                    selectedServices
                                }
                            }
                    ) {
                        Checkbox(
                            checked = selectedServices.contains(service),
                            onCheckedChange = { checked ->
                                selectedServices = if (checked && selectedServices.size < 4) {
                                    selectedServices + service
                                } else if (!checked) {
                                    selectedServices - service
                                } else {
                                    selectedServices
                                }
                            }
                        )
                        Text(
                            service,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(selectedServices.toList()) },
                enabled = selectedServices.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HotPink,
                    contentColor = Color.White
                )
            ) {
                Text("Add to Cart (${selectedServices.size}/4)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = HotPink)
            }
        }
    )
}

@Composable
fun BookingSuccessDialog(
    bookingDetails: BookingDetails,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Booking Confirmed!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark
            )
        },
        text = {
            Column {
                Text(
                    "Your package has been successfully booked.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "Package: ${bookingDetails.packageName}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Date: ${bookingDetails.date} at ${bookingDetails.time}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "Total: ₹${"%.0f".format(bookingDetails.totalPrice)}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = HotPink
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HotPink,
                    contentColor = Color.White
                )
            ) {
                Text("OK")
            }
        }
    )
}

@Composable
fun CouponDialog(
    offers: List<Offer>,
    appliedCoupon: Offer?,
    onDismiss: () -> Unit,
    onApplyCoupon: (Offer) -> Unit,
    onRemoveCoupon: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (appliedCoupon != null) "Applied Coupon" else "Available Coupons",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (appliedCoupon != null) {
                    CouponItem(
                        offer = appliedCoupon,
                        isApplied = true,
                        onApply = {},
                        onRemove = onRemoveCoupon
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
                if (offers.isNotEmpty()) {
                    Text(
                        "Available Coupons:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    offers.filter { it.id != appliedCoupon?.id }.forEach { offer ->
                        CouponItem(
                            offer = offer,
                            isApplied = false,
                            onApply = { onApplyCoupon(offer) },
                            onRemove = {})
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Text("No coupons available", style = MaterialTheme.typography.bodyMedium)
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
    )
}

@Composable
fun CouponItem(offer: Offer, isApplied: Boolean, onApply: () -> Unit, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isApplied) Pink10 else Color.White),
        border = BorderStroke(1.dp, if (isApplied) HotPink else Pink40)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        offer.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                    Text(
                        offer.name,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Pink80
                    )
                }
                if (isApplied) {
                    Button(
                        onClick = onRemove,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Remove")
                    }
                } else {
                    Button(
                        onClick = onApply,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Apply")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                offer.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Use code: ${offer.useCode}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = HotPink
                )
                Text(
                    "${offer.discountPercentage}% OFF",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = HotPink
                )
            }
        }
    }
}

@Composable
fun ErrorView(errorMessage: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = HotPink,
                contentColor = Color.White
            )
        ) {
            Text("Retry")
        }
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Text("Loading...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RatingDialog(
    itemName: String,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    var rating by remember { mutableStateOf(currentRating) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Rate $itemName",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark
            )
        },
        text = {
            Column {
                Text(
                    "How would you rate this service?",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "$i star",
                            tint = HotPink,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { rating = i }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onRatingChanged(rating); onSubmit() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HotPink,
                    contentColor = Color.White
                )
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = HotPink)
            }
        }
    )
}

@Composable
fun ContentTypeSelector(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) HotPink else Pink10,
        border = BorderStroke(1.dp, if (selected) HotPink else Pink40),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) Color.White else HotPinkDark,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}