//////////////////package com.beauty.parler.activity
//////////////////
//////////////////import android.app.DatePickerDialog
//////////////////import android.app.TimePickerDialog
//////////////////import android.content.ActivityNotFoundException
//////////////////import android.content.Context
//////////////////import android.content.Intent
//////////////////import android.net.Uri
//////////////////import android.util.Log
//////////////////import android.widget.Toast
//////////////////import androidx.compose.foundation.BorderStroke
//////////////////import androidx.compose.foundation.background
//////////////////import androidx.compose.foundation.clickable
//////////////////import androidx.compose.foundation.layout.Arrangement
//////////////////import androidx.compose.foundation.layout.Box
//////////////////import androidx.compose.foundation.layout.Column
//////////////////import androidx.compose.foundation.layout.Row
//////////////////import androidx.compose.foundation.layout.Spacer
//////////////////import androidx.compose.foundation.layout.fillMaxSize
//////////////////import androidx.compose.foundation.layout.fillMaxWidth
//////////////////import androidx.compose.foundation.layout.height
//////////////////import androidx.compose.foundation.layout.padding
//////////////////import androidx.compose.foundation.layout.size
//////////////////import androidx.compose.foundation.layout.width
//////////////////import androidx.compose.foundation.lazy.LazyColumn
//////////////////import androidx.compose.foundation.lazy.items
//////////////////import androidx.compose.foundation.rememberScrollState
//////////////////import androidx.compose.foundation.shape.CircleShape
//////////////////import androidx.compose.foundation.shape.RoundedCornerShape
//////////////////import androidx.compose.foundation.text.KeyboardOptions
//////////////////import androidx.compose.foundation.verticalScroll
//////////////////import androidx.compose.material.icons.Icons
//////////////////import androidx.compose.material.icons.filled.AccessTime
//////////////////import androidx.compose.material.icons.filled.Add
//////////////////import androidx.compose.material.icons.filled.ArrowBack
//////////////////import androidx.compose.material.icons.filled.CalendarToday
//////////////////import androidx.compose.material.icons.filled.CheckCircle
//////////////////import androidx.compose.material.icons.filled.Delete
//////////////////import androidx.compose.material.icons.filled.Remove
//////////////////import androidx.compose.material.icons.filled.ShoppingCart
//////////////////import androidx.compose.material.icons.filled.Spa
//////////////////import androidx.compose.material3.AlertDialog
//////////////////import androidx.compose.material3.Button
//////////////////import androidx.compose.material3.ButtonDefaults
//////////////////import androidx.compose.material3.Card
//////////////////import androidx.compose.material3.CardDefaults
//////////////////import androidx.compose.material3.CenterAlignedTopAppBar
//////////////////import androidx.compose.material3.CircularProgressIndicator
//////////////////import androidx.compose.material3.Divider
//////////////////import androidx.compose.material3.ExperimentalMaterial3Api
//////////////////import androidx.compose.material3.Icon
//////////////////import androidx.compose.material3.IconButton
//////////////////import androidx.compose.material3.MaterialTheme
//////////////////import androidx.compose.material3.OutlinedButton
//////////////////import androidx.compose.material3.OutlinedTextField
//////////////////import androidx.compose.material3.OutlinedTextFieldDefaults
//////////////////import androidx.compose.material3.Scaffold
//////////////////import androidx.compose.material3.Surface
//////////////////import androidx.compose.material3.Text
//////////////////import androidx.compose.material3.TextButton
//////////////////import androidx.compose.material3.TopAppBarDefaults
//////////////////import androidx.compose.runtime.Composable
//////////////////import androidx.compose.runtime.LaunchedEffect
//////////////////import androidx.compose.runtime.collectAsState
//////////////////import androidx.compose.runtime.getValue
//////////////////import androidx.compose.runtime.mutableStateOf
//////////////////import androidx.compose.runtime.remember
//////////////////import androidx.compose.runtime.setValue
//////////////////import androidx.compose.ui.Alignment
//////////////////import androidx.compose.ui.Modifier
//////////////////import androidx.compose.ui.draw.clip
//////////////////import androidx.compose.ui.draw.shadow
//////////////////import androidx.compose.ui.graphics.Brush
//////////////////import androidx.compose.ui.graphics.Color
//////////////////import androidx.compose.ui.layout.ContentScale
//////////////////import androidx.compose.ui.platform.LocalContext
//////////////////import androidx.compose.ui.text.font.FontWeight
//////////////////import androidx.compose.ui.text.input.KeyboardType
//////////////////import androidx.compose.ui.text.style.TextAlign
//////////////////import androidx.compose.ui.text.style.TextDecoration
//////////////////import androidx.compose.ui.unit.dp
//////////////////import androidx.compose.ui.unit.sp
//////////////////import androidx.lifecycle.viewmodel.compose.viewModel
//////////////////import androidx.navigation.NavController
//////////////////import coil.compose.AsyncImage
//////////////////import com.beauty.parler.model.CartItem
//////////////////import com.beauty.parler.shared.CartRepository
//////////////////import com.beauty.parler.shared.CartViewModel
//////////////////import com.beauty.parler.shared.CartViewModelFactory
//////////////////import com.beauty.parler.ui.theme.HotPink
//////////////////import com.beauty.parler.ui.theme.HotPinkDark
//////////////////import com.beauty.parler.ui.theme.Pink10
//////////////////import com.beauty.parler.ui.theme.Pink20
//////////////////import com.beauty.parler.ui.theme.Pink40
//////////////////import com.beauty.parler.ui.theme.Pink80
//////////////////import com.google.firebase.firestore.FirebaseFirestore
//////////////////import kotlinx.coroutines.tasks.await
//////////////////import java.net.URLEncoder
//////////////////import java.text.SimpleDateFormat
//////////////////import java.util.Calendar
//////////////////import java.util.Locale
//////////////////
//////////////////data class Coupon(
//////////////////    val id: String = "",
//////////////////    val code: String = "",
//////////////////    val discountPercentage: Int = 0,
//////////////////    val title: String = "",
//////////////////    val description: String = "",
//////////////////    val validUntil: Long = 0L,
//////////////////    val formattedDate: String = "",
//////////////////    val maxDiscount: Double? = null
//////////////////)
//////////////////
//////////////////@OptIn(ExperimentalMaterial3Api::class)
//////////////////@Composable
//////////////////fun CartScreen(navController: NavController, viewModel: CartViewModel) {
//////////////////    val context = LocalContext.current
//////////////////    val cartRepository = remember { CartRepository(context) }
//////////////////    val factory = remember { CartViewModelFactory(cartRepository) }
//////////////////    val cartViewModel: CartViewModel = viewModel(factory = factory)
//////////////////    val cartItems by cartViewModel.cartItems.collectAsState()
//////////////////    var showCouponDialog by remember { mutableStateOf(false) }
//////////////////    var couponCode by remember { mutableStateOf("") }
//////////////////    var selectedCoupon by remember { mutableStateOf<Coupon?>(null) }
//////////////////    var showBookingDialog by remember { mutableStateOf(false) }
//////////////////    var availableCoupons by remember { mutableStateOf<List<Coupon>>(emptyList()) }
//////////////////    var isLoadingCoupons by remember { mutableStateOf(false) }
//////////////////    var couponError by remember { mutableStateOf<String?>(null) }
//////////////////
//////////////////    val whatsappNumber = "+919288302255"
//////////////////    val db = FirebaseFirestore.getInstance()
//////////////////
//////////////////    // Load coupons from Firestore
//////////////////    LaunchedEffect(Unit) {
//////////////////        isLoadingCoupons = true
//////////////////        try {
//////////////////            val result = db.collection("offers")
//////////////////                .whereGreaterThanOrEqualTo("validDate", System.currentTimeMillis())
//////////////////                .get()
//////////////////                .await()
//////////////////
//////////////////            availableCoupons = result.documents.mapNotNull { doc ->
//////////////////                try {
//////////////////                    Coupon(
//////////////////                        id = doc.id,
//////////////////                        code = doc.getString("useCode") ?: "",
//////////////////                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
//////////////////                        title = doc.getString("title") ?: "",
//////////////////                        description = doc.getString("description") ?: "",
//////////////////                        validUntil = doc.getLong("validDate") ?: 0L,
//////////////////                        formattedDate = doc.getString("formattedDate") ?: "",
//////////////////                        maxDiscount = doc.getDouble("maxDiscount")
//////////////////                    )
//////////////////                } catch (e: Exception) {
//////////////////                    Log.e("CartScreen", "Error parsing coupon ${doc.id}", e)
//////////////////                    null
//////////////////                }
//////////////////            }
//////////////////        } catch (e: Exception) {
//////////////////            couponError = "Failed to load coupons. Please try again."
//////////////////            Log.e("CartScreen", "Error loading coupons", e)
//////////////////        } finally {
//////////////////            isLoadingCoupons = false
//////////////////        }
//////////////////    }
//////////////////
//////////////////    // Calculate prices
//////////////////    val subtotal = calculateSubtotal(cartItems)
//////////////////    val discount = calculateDiscount(cartItems, selectedCoupon)
//////////////////    val total = subtotal - discount
//////////////////
//////////////////    Scaffold(
//////////////////        topBar = {
//////////////////            CenterAlignedTopAppBar(
//////////////////                title = {
//////////////////                    Text(
//////////////////                        "Your Cart",
//////////////////                        style = MaterialTheme.typography.titleLarge,
//////////////////                        fontWeight = FontWeight.Bold,
//////////////////                        color = HotPinkDark
//////////////////                    )
//////////////////                },
//////////////////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//////////////////                    containerColor = Color.White
//////////////////                ),
//////////////////                navigationIcon = {
//////////////////                    IconButton(onClick = { navController.popBackStack() }) {
//////////////////                        Icon(
//////////////////                            imageVector = Icons.Default.ArrowBack,
//////////////////                            contentDescription = "Back",
//////////////////                            tint = HotPink
//////////////////                        )
//////////////////                    }
//////////////////                }
//////////////////            )
//////////////////        },
//////////////////        bottomBar = {
//////////////////            Surface(
//////////////////                modifier = Modifier
//////////////////                    .fillMaxWidth()
//////////////////                    .shadow(elevation = 16.dp),
//////////////////                color = Color.White
//////////////////            ) {
//////////////////                Column(
//////////////////                    modifier = Modifier.padding(16.dp)
//////////////////                ) {
//////////////////                    PriceRow(
//////////////////                        label = "Subtotal (${cartItems.size} items):",
//////////////////                        amount = subtotal
//////////////////                    )
//////////////////
//////////////////                    if (selectedCoupon != null) {
//////////////////                        PriceRow(
//////////////////                            label = "Discount (${selectedCoupon?.code}):",
//////////////////                            amount = -discount,
//////////////////                            color = HotPink
//////////////////                        )
//////////////////                    }
//////////////////
//////////////////                    Divider(modifier = Modifier.padding(vertical = 8.dp))
//////////////////
//////////////////                    PriceRow(
//////////////////                        label = "Total Amount:",
//////////////////                        amount = total,
//////////////////                        color = HotPinkDark,
//////////////////                        fontWeight = FontWeight.Bold
//////////////////                    )
//////////////////
//////////////////                    Spacer(modifier = Modifier.height(16.dp))
//////////////////
//////////////////                    Button(
//////////////////                        onClick = { showBookingDialog = true },
//////////////////                        modifier = Modifier
//////////////////                            .fillMaxWidth()
//////////////////                            .height(50.dp),
//////////////////                        colors = ButtonDefaults.buttonColors(
//////////////////                            containerColor = HotPink,
//////////////////                            contentColor = Color.White
//////////////////                        ),
//////////////////                        shape = RoundedCornerShape(12.dp),
//////////////////                        enabled = cartItems.isNotEmpty(),
//////////////////                        elevation = ButtonDefaults.buttonElevation(
//////////////////                            defaultElevation = 8.dp,
//////////////////                            pressedElevation = 4.dp
//////////////////                        )
//////////////////                    ) {
//////////////////                        Text(
//////////////////                            "Book Appointment",
//////////////////                            fontWeight = FontWeight.Bold,
//////////////////                            fontSize = 16.sp
//////////////////                        )
//////////////////                    }
//////////////////                }
//////////////////            }
//////////////////        }
//////////////////    ) { innerPadding ->
//////////////////        Box(modifier = Modifier.fillMaxSize()) {
//////////////////            Box(
//////////////////                modifier = Modifier
//////////////////                    .fillMaxSize()
//////////////////                    .background(
//////////////////                        brush = Brush.verticalGradient(
//////////////////                            colors = listOf(Pink10, Color.White),
//////////////////                            startY = 0f,
//////////////////                            endY = 500f
//////////////////                        )
//////////////////                    )
//////////////////            )
//////////////////
//////////////////            LazyColumn(
//////////////////                modifier = Modifier
//////////////////                    .padding(innerPadding)
//////////////////                    .fillMaxSize()
//////////////////            ) {
//////////////////                if (cartItems.isEmpty()) {
//////////////////                    item {
//////////////////                        EmptyCartView(navController)
//////////////////                    }
//////////////////                } else {
//////////////////                    item {
//////////////////                        Text(
//////////////////                            text = "Scheduled Services",
//////////////////                            style = MaterialTheme.typography.titleMedium,
//////////////////                            fontWeight = FontWeight.Bold,
//////////////////                            color = HotPinkDark,
//////////////////                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//////////////////                        )
//////////////////                    }
//////////////////
//////////////////                    items(cartItems) { cartItem ->
//////////////////                        CartItemCard(
//////////////////                            cartItem = cartItem,
//////////////////                            onQuantityChange = { newQuantity ->
//////////////////                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
//////////////////                            },
//////////////////                            onRemove = {
//////////////////                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
//////////////////                            }
//////////////////                        )
//////////////////                    }
//////////////////                }
//////////////////            }
//////////////////        }
//////////////////    }
//////////////////
//////////////////    // Booking Confirmation Dialog
//////////////////    if (showBookingDialog) {
//////////////////        BookingConfirmationDialog(
//////////////////            cartItems = cartItems,
//////////////////            subtotal = subtotal,
//////////////////            discount = discount,
//////////////////            total = total,
//////////////////            onDismiss = { showBookingDialog = false },
//////////////////            onConfirm = { customerName, phoneNumber, address, date, time ->
//////////////////                sendWhatsAppBooking(
//////////////////                    context = context,
//////////////////                    whatsappNumber = whatsappNumber,
//////////////////                    cartItems = cartItems,
//////////////////                    subtotal = subtotal,
//////////////////                    discount = discount,
//////////////////                    total = total,
//////////////////                    coupon = selectedCoupon,
//////////////////                    customerName = customerName,
//////////////////                    phoneNumber = phoneNumber,
//////////////////                    address = address,
//////////////////                    date = date,
//////////////////                    time = time
//////////////////                )
//////////////////                showBookingDialog = false
//////////////////            }
//////////////////        )
//////////////////    }
//////////////////}
//////////////////
//////////////////@Composable
//////////////////private fun BookingConfirmationDialog(
//////////////////    cartItems: List<CartItem>,
//////////////////    subtotal: Double,
//////////////////    discount: Double,
//////////////////    total: Double,
//////////////////    onDismiss: () -> Unit,
//////////////////    onConfirm: (String, String, String, String, String) -> Unit
//////////////////) {
//////////////////    var customerName by remember { mutableStateOf("") }
//////////////////    var phoneNumber by remember { mutableStateOf("") }
//////////////////    var address by remember { mutableStateOf("") }
//////////////////    var date by remember { mutableStateOf("") }
//////////////////    var time by remember { mutableStateOf("") }
//////////////////    val context = LocalContext.current
//////////////////
//////////////////    AlertDialog(
//////////////////        onDismissRequest = onDismiss,
//////////////////        title = {
//////////////////            Text(
//////////////////                "Book Appointment",
//////////////////                style = MaterialTheme.typography.titleLarge,
//////////////////                color = HotPinkDark
//////////////////            )
//////////////////        },
//////////////////        text = {
//////////////////            Column(
//////////////////                modifier = Modifier.verticalScroll(rememberScrollState())
//////////////////            ) {
//////////////////                // Customer Information Inputs
//////////////////                OutlinedTextField(
//////////////////                    value = customerName,
//////////////////                    onValueChange = { customerName = it },
//////////////////                    label = { Text("Customer Name") },
//////////////////                    modifier = Modifier.fillMaxWidth(),
//////////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////////                        focusedBorderColor = HotPink,
//////////////////                        focusedLabelColor = HotPink
//////////////////                    )
//////////////////                )
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                OutlinedTextField(
//////////////////                    value = phoneNumber,
//////////////////                    onValueChange = { phoneNumber = it },
//////////////////                    label = { Text("Phone Number") },
//////////////////                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//////////////////                    modifier = Modifier.fillMaxWidth(),
//////////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////////                        focusedBorderColor = HotPink,
//////////////////                        focusedLabelColor = HotPink
//////////////////                    )
//////////////////                )
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                OutlinedTextField(
//////////////////                    value = address,
//////////////////                    onValueChange = { address = it },
//////////////////                    label = { Text("Address") },
//////////////////                    modifier = Modifier.fillMaxWidth(),
//////////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////////                        focusedBorderColor = HotPink,
//////////////////                        focusedLabelColor = HotPink
//////////////////                    )
//////////////////                )
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                Row(
//////////////////                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//////////////////                ) {
//////////////////                    OutlinedTextField(
//////////////////                        value = date,
//////////////////                        onValueChange = { date = it },
//////////////////                        label = { Text("Date") },
//////////////////                        modifier = Modifier.weight(1f),
//////////////////                        colors = OutlinedTextFieldDefaults.colors(
//////////////////                            focusedBorderColor = HotPink,
//////////////////                            focusedLabelColor = HotPink
//////////////////                        ),
//////////////////                        readOnly = true,
//////////////////                        trailingIcon = {
//////////////////                            IconButton(onClick = {
//////////////////                                showDatePicker(context) { selectedDate ->
//////////////////                                    date = selectedDate
//////////////////                                }
//////////////////                            }) {
//////////////////                                Icon(
//////////////////                                    Icons.Default.CalendarToday,
//////////////////                                    contentDescription = "Pick Date",
//////////////////                                    tint = HotPink
//////////////////                                )
//////////////////                            }
//////////////////                        }
//////////////////                    )
//////////////////
//////////////////                    OutlinedTextField(
//////////////////                        value = time,
//////////////////                        onValueChange = { time = it },
//////////////////                        label = { Text("Time") },
//////////////////                        modifier = Modifier.weight(1f),
//////////////////                        colors = OutlinedTextFieldDefaults.colors(
//////////////////                            focusedBorderColor = HotPink,
//////////////////                            focusedLabelColor = HotPink
//////////////////                        ),
//////////////////                        readOnly = true,
//////////////////                        trailingIcon = {
//////////////////                            IconButton(onClick = {
//////////////////                                showTimePicker(context) { selectedTime ->
//////////////////                                    time = selectedTime
//////////////////                                }
//////////////////                            }) {
//////////////////                                Icon(
//////////////////                                    Icons.Default.AccessTime,
//////////////////                                    contentDescription = "Pick Time",
//////////////////                                    tint = HotPink
//////////////////                                )
//////////////////                            }
//////////////////                        }
//////////////////                    )
//////////////////                }
//////////////////
//////////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////////
//////////////////                // Services summary
//////////////////                Text(
//////////////////                    "Services Booked:",
//////////////////                    style = MaterialTheme.typography.bodyLarge,
//////////////////                    fontWeight = FontWeight.Bold,
//////////////////                    color = HotPinkDark
//////////////////                )
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                cartItems.forEach { item ->
//////////////////                    Text(
//////////////////                        "- ${item.name} (₹${item.price})",
//////////////////                        color = Color.Gray
//////////////////                    )
//////////////////                }
//////////////////
//////////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////////
//////////////////                PriceRow("Subtotal:", subtotal)
//////////////////
//////////////////                if (discount > 0) {
//////////////////                    PriceRow("Discount:", -discount, HotPink)
//////////////////                }
//////////////////
//////////////////                Divider(modifier = Modifier.padding(vertical = 8.dp))
//////////////////
//////////////////                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)
//////////////////
//////////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////////
//////////////////                Text(
//////////////////                    "A WhatsApp message will be sent to confirm your appointment",
//////////////////                    style = MaterialTheme.typography.bodySmall,
//////////////////                    color = Color.Gray
//////////////////                )
//////////////////            }
//////////////////        },
//////////////////        confirmButton = {
//////////////////            Button(
//////////////////                onClick = {
//////////////////                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
//////////////////                        address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
//////////////////                        onConfirm(customerName, phoneNumber, address, date, time)
//////////////////                    } else {
//////////////////                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//////////////////                    }
//////////////////                },
//////////////////                colors = ButtonDefaults.buttonColors(
//////////////////                    containerColor = HotPink,
//////////////////                    contentColor = Color.White
//////////////////                ),
//////////////////                shape = RoundedCornerShape(12.dp)
//////////////////            ) {
//////////////////                Text("Confirm & Send WhatsApp")
//////////////////            }
//////////////////        },
//////////////////        dismissButton = {
//////////////////            TextButton(
//////////////////                onClick = onDismiss,
//////////////////                shape = RoundedCornerShape(12.dp)
//////////////////            ) {
//////////////////                Text("Cancel", color = HotPink)
//////////////////            }
//////////////////        },
//////////////////        containerColor = Color.White,
//////////////////        shape = RoundedCornerShape(24.dp)
//////////////////    )
//////////////////}
//////////////////
//////////////////private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
//////////////////    val calendar = Calendar.getInstance()
//////////////////    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//////////////////        calendar.set(Calendar.YEAR, year)
//////////////////        calendar.set(Calendar.MONTH, month)
//////////////////        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//////////////////
//////////////////        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//////////////////        onDateSelected(sdf.format(calendar.time))
//////////////////    }
//////////////////
//////////////////    DatePickerDialog(
//////////////////        context,
//////////////////        dateSetListener,
//////////////////        calendar.get(Calendar.YEAR),
//////////////////        calendar.get(Calendar.MONTH),
//////////////////        calendar.get(Calendar.DAY_OF_MONTH)
//////////////////    ).apply {
//////////////////        datePicker.minDate = System.currentTimeMillis() - 1000
//////////////////        show()
//////////////////    }
//////////////////}
//////////////////
//////////////////private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
//////////////////    val calendar = Calendar.getInstance()
//////////////////    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//////////////////        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//////////////////        calendar.set(Calendar.MINUTE, minute)
//////////////////
//////////////////        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//////////////////        onTimeSelected(sdf.format(calendar.time))
//////////////////    }
//////////////////
//////////////////    TimePickerDialog(
//////////////////        context,
//////////////////        timeSetListener,
//////////////////        calendar.get(Calendar.HOUR_OF_DAY),
//////////////////        calendar.get(Calendar.MINUTE),
//////////////////        false
//////////////////    ).show()
//////////////////}
//////////////////
//////////////////private fun sendWhatsAppBooking(
//////////////////    context: Context,
//////////////////    whatsappNumber: String,
//////////////////    cartItems: List<CartItem>,
//////////////////    subtotal: Double,
//////////////////    discount: Double,
//////////////////    total: Double,
//////////////////    coupon: Coupon?,
//////////////////    customerName: String,
//////////////////    phoneNumber: String,
//////////////////    address: String,
//////////////////    date: String,
//////////////////    time: String
//////////////////) {
//////////////////    val message = buildString {
//////////////////        append("🌟 *Beauty Parlor Booking* 🌟\n\n")
//////////////////
//////////////////        // Customer information
//////////////////        append("*Customer Details:*\n")
//////////////////        append("Name: $customerName\n")
//////////////////        append("Phone: $phoneNumber\n")
//////////////////        append("Address: $address\n")
//////////////////        append("Appointment Date: $date\n")
//////////////////        append("Appointment Time: $time\n\n")
//////////////////
//////////////////        // Services list
//////////////////        append("*Services Booked:*\n")
//////////////////        cartItems.forEachIndexed { index, item ->
//////////////////            append("${index + 1}. ${item.name} - ₹${item.price}")
//////////////////            if (item.quantity > 1) append(" (Qty: ${item.quantity})")
//////////////////            append("\n")
//////////////////        }
//////////////////
//////////////////        // Payment summary
//////////////////        append("\n*Payment Summary:*\n")
//////////////////        append("Subtotal: ₹${"%.2f".format(subtotal)}\n")
//////////////////        if (discount > 0) {
//////////////////            append("Discount: -₹${"%.2f".format(discount)}")
//////////////////            coupon?.let { append(" (Coupon: ${it.code})") }
//////////////////            append("\n")
//////////////////        }
//////////////////        append("Total: ₹${"%.2f".format(total)}\n\n")
//////////////////
//////////////////        // Closing message
//////////////////        append("Please confirm my appointment. Thank you! 💖")
//////////////////    }
//////////////////
//////////////////    try {
//////////////////        val url = "https://wa.me/$whatsappNumber?text=${URLEncoder.encode(message, "UTF-8")}"
//////////////////        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//////////////////            setPackage("com.whatsapp")
//////////////////        }
//////////////////        context.startActivity(intent)
//////////////////    } catch (e: ActivityNotFoundException) {
//////////////////        Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
//////////////////    }
//////////////////}
//////////////////
//////////////////@Composable
//////////////////private fun EmptyCartView(navController: NavController) {
//////////////////    Column(
//////////////////        modifier = Modifier
//////////////////            .fillMaxSize()
//////////////////            .padding(32.dp),
//////////////////        horizontalAlignment = Alignment.CenterHorizontally,
//////////////////        verticalArrangement = Arrangement.Center
//////////////////    ) {
//////////////////        Icon(
//////////////////            imageVector = Icons.Default.ShoppingCart,
//////////////////            contentDescription = "Empty Cart",
//////////////////            tint = Pink40,
//////////////////            modifier = Modifier.size(64.dp)
//////////////////        )
//////////////////        Spacer(modifier = Modifier.height(16.dp))
//////////////////        Text(
//////////////////            "Your cart is empty",
//////////////////            style = MaterialTheme.typography.titleLarge,
//////////////////            color = HotPinkDark
//////////////////        )
//////////////////        Spacer(modifier = Modifier.height(8.dp))
//////////////////        Text(
//////////////////            "Browse our services and add something special",
//////////////////            style = MaterialTheme.typography.bodyMedium,
//////////////////            color = Pink80,
//////////////////            textAlign = TextAlign.Center
//////////////////        )
//////////////////        Spacer(modifier = Modifier.height(24.dp))
//////////////////        Button(
//////////////////            onClick = {
//////////////////                navController.navigate("services") {
//////////////////                    popUpTo("home") { saveState = true }
//////////////////                    launchSingleTop = true
//////////////////                    restoreState = true
//////////////////                }
//////////////////            },
//////////////////            colors = ButtonDefaults.buttonColors(
//////////////////                containerColor = HotPink,
//////////////////                contentColor = Color.White
//////////////////            ),
//////////////////            shape = RoundedCornerShape(12.dp)
//////////////////        ) {
//////////////////            Text("Explore Services")
//////////////////        }
//////////////////    }
//////////////////}
//////////////////
//////////////////@Composable
//////////////////private fun CartItemCard(
//////////////////    cartItem: CartItem,
//////////////////    onQuantityChange: (Int) -> Unit,
//////////////////    onRemove: () -> Unit
//////////////////) {
//////////////////    Card(
//////////////////        modifier = Modifier
//////////////////            .padding(horizontal = 16.dp, vertical = 8.dp)
//////////////////            .fillMaxWidth(),
//////////////////        shape = RoundedCornerShape(20.dp),
//////////////////        colors = CardDefaults.cardColors(containerColor = Color.White),
//////////////////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//////////////////    ) {
//////////////////        Row(modifier = Modifier.padding(16.dp)) {
//////////////////            Box(
//////////////////                modifier = Modifier
//////////////////                    .size(100.dp)
//////////////////                    .clip(RoundedCornerShape(12.dp))
//////////////////                    .background(Pink10)
//////////////////            ) {
//////////////////                if (cartItem.imageUrl.isNotEmpty()) {
//////////////////                    AsyncImage(
//////////////////                        model = cartItem.imageUrl,
//////////////////                        contentDescription = cartItem.name,
//////////////////                        contentScale = ContentScale.Crop,
//////////////////                        modifier = Modifier.fillMaxSize()
//////////////////                    )
//////////////////                } else {
//////////////////                    Icon(
//////////////////                        imageVector = Icons.Default.Spa,
//////////////////                        contentDescription = "Service Image",
//////////////////                        tint = HotPink,
//////////////////                        modifier = Modifier
//////////////////                            .size(60.dp)
//////////////////                            .align(Alignment.Center)
//////////////////                    )
//////////////////                }
//////////////////            }
//////////////////
//////////////////            Spacer(modifier = Modifier.width(16.dp))
//////////////////
//////////////////            Column(modifier = Modifier.weight(1f)) {
//////////////////                Text(
//////////////////                    cartItem.name,
//////////////////                    style = MaterialTheme.typography.titleSmall,
//////////////////                    fontWeight = FontWeight.Bold,
//////////////////                    color = HotPinkDark
//////////////////                )
//////////////////
////////////////////                Spacer(modifier = Modifier.height(4.dp))
//////////////////
////////////////////                cartItem.dateTime?.let {
////////////////////                    Text(
////////////////////                        it,
////////////////////                        style = MaterialTheme.typography.labelSmall,
////////////////////                        color = Pink80
////////////////////                    )
////////////////////                }
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                Row(verticalAlignment = Alignment.CenterVertically) {
//////////////////                    Text(
//////////////////                        "₹${cartItem.price}",
//////////////////                        style = MaterialTheme.typography.bodyMedium,
//////////////////                        fontWeight = FontWeight.Bold,
//////////////////                        color = HotPink
//////////////////                    )
//////////////////
//////////////////                    if (cartItem.originalPrice != null) {
//////////////////                        Spacer(modifier = Modifier.width(8.dp))
//////////////////                        Text(
//////////////////                            "₹${cartItem.originalPrice}",
//////////////////                            style = MaterialTheme.typography.labelSmall,
//////////////////                            color = Color.Gray,
//////////////////                            textDecoration = TextDecoration.LineThrough
//////////////////                        )
//////////////////                    }
//////////////////                }
//////////////////
//////////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////////
//////////////////                Row(
//////////////////                    verticalAlignment = Alignment.CenterVertically,
//////////////////                    horizontalArrangement = Arrangement.SpaceBetween,
//////////////////                    modifier = Modifier.fillMaxWidth()
//////////////////                ) {
//////////////////                    // Quantity selector
//////////////////                    QuantitySelector(
//////////////////                        quantity = cartItem.quantity,
//////////////////                        onQuantityChange = onQuantityChange
//////////////////                    )
//////////////////
//////////////////                    // Remove button
//////////////////                    IconButton(
//////////////////                        onClick = onRemove,
//////////////////                        modifier = Modifier
//////////////////                            .size(32.dp)
//////////////////                            .clip(CircleShape)
//////////////////                    ) {
//////////////////                        Icon(
//////////////////                            imageVector = Icons.Default.Delete,
//////////////////                            contentDescription = "Remove",
//////////////////                            tint = HotPink
//////////////////                        )
//////////////////                    }
//////////////////                }
//////////////////            }
//////////////////        }
//////////////////    }
//////////////////}
//////////////////
//////////////////
//////////////////
//////////////////@Composable
//////////////////private fun QuantitySelector(
//////////////////    quantity: Int,
//////////////////    onQuantityChange: (Int) -> Unit
//////////////////) {
//////////////////    Surface(
//////////////////        shape = RoundedCornerShape(12.dp),
//////////////////        border = BorderStroke(1.dp, Pink20),
//////////////////        modifier = Modifier.height(32.dp)
//////////////////    ) {
//////////////////        Row(
//////////////////            verticalAlignment = Alignment.CenterVertically,
//////////////////            horizontalArrangement = Arrangement.Center
//////////////////        ) {
//////////////////            IconButton(
//////////////////                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
//////////////////                modifier = Modifier.size(32.dp)
//////////////////            ) {
//////////////////                Icon(
//////////////////                    imageVector = Icons.Default.Remove,
//////////////////                    contentDescription = "Decrease",
//////////////////                    tint = if (quantity > 1) HotPink else Color.LightGray
//////////////////                )
//////////////////            }
//////////////////
//////////////////            Text(
//////////////////                text = quantity.toString(),
//////////////////                style = MaterialTheme.typography.bodyMedium,
//////////////////                fontWeight = FontWeight.Bold,
//////////////////                color = HotPinkDark,
//////////////////                modifier = Modifier.padding(horizontal = 8.dp)
//////////////////            )
//////////////////
//////////////////            IconButton(
//////////////////                onClick = { onQuantityChange(quantity + 1) },
//////////////////                modifier = Modifier.size(32.dp)
//////////////////            ) {
//////////////////                Icon(
//////////////////                    imageVector = Icons.Default.Add,
//////////////////                    contentDescription = "Increase",
//////////////////                    tint = HotPink
//////////////////                )
//////////////////            }
//////////////////        }
//////////////////    }
//////////////////}
//////////////////
//////////////////@Composable
//////////////////private fun PriceRow(
//////////////////    label: String,
//////////////////    amount: Double,
//////////////////    color: Color = Color.Gray,
//////////////////    fontWeight: FontWeight = FontWeight.Normal
//////////////////) {
//////////////////    Row(
//////////////////        modifier = Modifier.fillMaxWidth(),
//////////////////        horizontalArrangement = Arrangement.SpaceBetween
//////////////////    ) {
//////////////////        Text(
//////////////////            label,
//////////////////            style = MaterialTheme.typography.bodyMedium,
//////////////////            color = color,
//////////////////            fontWeight = fontWeight
//////////////////        )
//////////////////        Text(
//////////////////            "₹${"%.2f".format(amount)}",
//////////////////            style = MaterialTheme.typography.bodyMedium,
//////////////////            color = color,
//////////////////            fontWeight = fontWeight
//////////////////        )
//////////////////    }
//////////////////}
//////////////////
//////////////////private fun calculateSubtotal(items: List<CartItem>): Double {
//////////////////    return items.sumOf { it.price * it.quantity }
//////////////////}
//////////////////
//////////////////private fun calculateDiscount(items: List<CartItem>, coupon: Coupon?): Double {
//////////////////    if (coupon == null) return 0.0
//////////////////    val subtotal = calculateSubtotal(items)
//////////////////    return (subtotal * coupon.discountPercentage / 100).coerceAtMost(
//////////////////        coupon.maxDiscount ?: Double.MAX_VALUE
//////////////////    )
//////////////////}
////////////////
////////////////
////////////////
////////////////
////////////////
////////////
////////////
//////////////
//////////////
//////////////
//////////////
//////////////package com.beauty.parler.activity
//////////////
//////////////import android.app.DatePickerDialog
//////////////import android.app.TimePickerDialog
//////////////import android.content.ActivityNotFoundException
//////////////import android.content.Context
//////////////import android.content.Intent
//////////////import android.net.Uri
//////////////import android.util.Log
//////////////import android.widget.Toast
//////////////import androidx.compose.foundation.BorderStroke
//////////////import androidx.compose.foundation.background
//////////////import androidx.compose.foundation.clickable
//////////////import androidx.compose.foundation.layout.Arrangement
//////////////import androidx.compose.foundation.layout.Box
//////////////import androidx.compose.foundation.layout.Column
//////////////import androidx.compose.foundation.layout.Row
//////////////import androidx.compose.foundation.layout.Spacer
//////////////import androidx.compose.foundation.layout.fillMaxSize
//////////////import androidx.compose.foundation.layout.fillMaxWidth
//////////////import androidx.compose.foundation.layout.height
//////////////import androidx.compose.foundation.layout.padding
//////////////import androidx.compose.foundation.layout.size
//////////////import androidx.compose.foundation.layout.width
//////////////import androidx.compose.foundation.lazy.LazyColumn
//////////////import androidx.compose.foundation.lazy.items
//////////////import androidx.compose.foundation.rememberScrollState
//////////////import androidx.compose.foundation.shape.CircleShape
//////////////import androidx.compose.foundation.shape.RoundedCornerShape
//////////////import androidx.compose.foundation.text.KeyboardOptions
//////////////import androidx.compose.foundation.verticalScroll
//////////////import androidx.compose.material.icons.Icons
//////////////import androidx.compose.material.icons.filled.AccessTime
//////////////import androidx.compose.material.icons.filled.Add
//////////////import androidx.compose.material.icons.filled.ArrowBack
//////////////import androidx.compose.material.icons.filled.CalendarToday
//////////////import androidx.compose.material.icons.filled.CheckCircle
//////////////import androidx.compose.material.icons.filled.Delete
//////////////import androidx.compose.material.icons.filled.Remove
//////////////import androidx.compose.material.icons.filled.ShoppingCart
//////////////import androidx.compose.material.icons.filled.Spa
//////////////import androidx.compose.material3.AlertDialog
//////////////import androidx.compose.material3.Button
//////////////import androidx.compose.material3.ButtonDefaults
//////////////import androidx.compose.material3.Card
//////////////import androidx.compose.material3.CardDefaults
//////////////import androidx.compose.material3.CenterAlignedTopAppBar
//////////////import androidx.compose.material3.CircularProgressIndicator
//////////////import androidx.compose.material3.Divider
//////////////import androidx.compose.material3.ExperimentalMaterial3Api
//////////////import androidx.compose.material3.Icon
//////////////import androidx.compose.material3.IconButton
//////////////import androidx.compose.material3.MaterialTheme
//////////////import androidx.compose.material3.OutlinedButton
//////////////import androidx.compose.material3.OutlinedTextField
//////////////import androidx.compose.material3.OutlinedTextFieldDefaults
//////////////import androidx.compose.material3.Scaffold
//////////////import androidx.compose.material3.Surface
//////////////import androidx.compose.material3.Text
//////////////import androidx.compose.material3.TextButton
//////////////import androidx.compose.material3.TopAppBarDefaults
//////////////import androidx.compose.runtime.Composable
//////////////import androidx.compose.runtime.LaunchedEffect
//////////////import androidx.compose.runtime.collectAsState
//////////////import androidx.compose.runtime.getValue
//////////////import androidx.compose.runtime.mutableStateOf
//////////////import androidx.compose.runtime.remember
//////////////import androidx.compose.runtime.setValue
//////////////import androidx.compose.ui.Alignment
//////////////import androidx.compose.ui.Modifier
//////////////import androidx.compose.ui.draw.clip
//////////////import androidx.compose.ui.draw.shadow
//////////////import androidx.compose.ui.graphics.Brush
//////////////import androidx.compose.ui.graphics.Color
//////////////import androidx.compose.ui.layout.ContentScale
//////////////import androidx.compose.ui.platform.LocalContext
//////////////import androidx.compose.ui.text.font.FontWeight
//////////////import androidx.compose.ui.text.input.KeyboardType
//////////////import androidx.compose.ui.text.style.TextAlign
//////////////import androidx.compose.ui.text.style.TextDecoration
//////////////import androidx.compose.ui.unit.dp
//////////////import androidx.compose.ui.unit.sp
//////////////import androidx.lifecycle.viewmodel.compose.viewModel
//////////////import androidx.navigation.NavController
//////////////import coil.compose.AsyncImage
//////////////import com.beauty.parler.model.CartItem
//////////////import com.beauty.parler.shared.CartRepository
//////////////import com.beauty.parler.shared.CartViewModel
//////////////import com.beauty.parler.shared.CartViewModelFactory
//////////////import com.beauty.parler.ui.theme.HotPink
//////////////import com.beauty.parler.ui.theme.HotPinkDark
//////////////import com.beauty.parler.ui.theme.Pink10
//////////////import com.beauty.parler.ui.theme.Pink20
//////////////import com.beauty.parler.ui.theme.Pink40
//////////////import com.beauty.parler.ui.theme.Pink80
//////////////import com.google.firebase.firestore.FirebaseFirestore
//////////////import kotlinx.coroutines.tasks.await
//////////////import java.net.URLEncoder
//////////////import java.text.SimpleDateFormat
//////////////import java.util.Calendar
//////////////import java.util.Locale
//////////////
//////////////data class Coupon(
//////////////    val id: String = "",
//////////////    val code: String = "",
//////////////    val discountPercentage: Int = 0,
//////////////    val title: String = "",
//////////////    val description: String = "",
//////////////    val validUntil: Long = 0L,
//////////////    val formattedDate: String = "",
//////////////    val maxDiscount: Double? = null
//////////////)
//////////////
//////////////@OptIn(ExperimentalMaterial3Api::class)
//////////////@Composable
//////////////fun CartScreen(navController: NavController, viewModel: CartViewModel) {
//////////////    val context = LocalContext.current
//////////////    val cartRepository = remember { CartRepository(context) }
//////////////    val factory = remember { CartViewModelFactory(cartRepository) }
//////////////    val cartViewModel: CartViewModel = viewModel(factory = factory)
//////////////    val cartItems by cartViewModel.cartItems.collectAsState()
//////////////    var showCouponDialog by remember { mutableStateOf(false) }
//////////////    var couponCode by remember { mutableStateOf("") }
//////////////    var selectedCoupon by remember { mutableStateOf<Coupon?>(null) }
//////////////    var showBookingDialog by remember { mutableStateOf(false) }
//////////////    var availableCoupons by remember { mutableStateOf<List<Coupon>>(emptyList()) }
//////////////    var isLoadingCoupons by remember { mutableStateOf(false) }
//////////////    var couponError by remember { mutableStateOf<String?>(null) }
//////////////
//////////////    val whatsappNumber = "+919288302255"
//////////////    val db = FirebaseFirestore.getInstance()
//////////////
//////////////    // Load coupons from Firestore
//////////////    LaunchedEffect(Unit) {
//////////////        isLoadingCoupons = true
//////////////        try {
//////////////            val result = db.collection("offers")
//////////////                .whereGreaterThanOrEqualTo("validDate", System.currentTimeMillis())
//////////////                .get()
//////////////                .await()
//////////////
//////////////            availableCoupons = result.documents.mapNotNull { doc ->
//////////////                try {
//////////////                    Coupon(
//////////////                        id = doc.id,
//////////////                        code = doc.getString("useCode") ?: "",
//////////////                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
//////////////                        title = doc.getString("title") ?: "",
//////////////                        description = doc.getString("description") ?: "",
//////////////                        validUntil = doc.getLong("validDate") ?: 0L,
//////////////                        formattedDate = doc.getString("formattedDate") ?: "",
//////////////                        maxDiscount = doc.getDouble("maxDiscount")
//////////////                    )
//////////////                } catch (e: Exception) {
//////////////                    Log.e("CartScreen", "Error parsing coupon ${doc.id}", e)
//////////////                    null
//////////////                }
//////////////            }
//////////////        } catch (e: Exception) {
//////////////            couponError = "Failed to load coupons. Please try again."
//////////////            Log.e("CartScreen", "Error loading coupons", e)
//////////////        } finally {
//////////////            isLoadingCoupons = false
//////////////        }
//////////////    }
//////////////
//////////////    // Calculate prices
//////////////    val subtotal = calculateSubtotal(cartItems)
//////////////    val discount = calculateDiscount(cartItems, selectedCoupon)
//////////////    val total = subtotal - discount
//////////////
//////////////    Scaffold(
//////////////        topBar = {
//////////////            CenterAlignedTopAppBar(
//////////////                title = {
//////////////                    Text(
//////////////                        "Your Cart",
//////////////                        style = MaterialTheme.typography.titleLarge,
//////////////                        fontWeight = FontWeight.Bold,
//////////////                        color = HotPinkDark
//////////////                    )
//////////////                },
//////////////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//////////////                    containerColor = Color.White
//////////////                ),
//////////////                navigationIcon = {
//////////////                    IconButton(onClick = { navController.popBackStack() }) {
//////////////                        Icon(
//////////////                            imageVector = Icons.Default.ArrowBack,
//////////////                            contentDescription = "Back",
//////////////                            tint = HotPink
//////////////                        )
//////////////                    }
//////////////                }
//////////////            )
//////////////        },
//////////////        bottomBar = {
//////////////            Surface(
//////////////                modifier = Modifier
//////////////                    .fillMaxWidth()
//////////////                    .shadow(elevation = 16.dp),
//////////////                color = Color.White
//////////////            ) {
//////////////                Column(
//////////////                    modifier = Modifier.padding(16.dp)
//////////////                ) {
//////////////                    PriceRow(
//////////////                        label = "Subtotal (${cartItems.size} items):",
//////////////                        amount = subtotal
//////////////                    )
//////////////
//////////////                    if (selectedCoupon != null) {
//////////////                        PriceRow(
//////////////                            label = "Discount (${selectedCoupon?.code}):",
//////////////                            amount = -discount,
//////////////                            color = HotPink
//////////////                        )
//////////////                    }
//////////////
//////////////                    Divider(modifier = Modifier.padding(vertical = 8.dp))
//////////////
//////////////                    PriceRow(
//////////////                        label = "Total Amount:",
//////////////                        amount = total,
//////////////                        color = HotPinkDark,
//////////////                        fontWeight = FontWeight.Bold
//////////////                    )
//////////////
//////////////                    Spacer(modifier = Modifier.height(16.dp))
//////////////
//////////////                    Button(
//////////////                        onClick = { showBookingDialog = true },
//////////////                        modifier = Modifier
//////////////                            .fillMaxWidth()
//////////////                            .height(50.dp),
//////////////                        colors = ButtonDefaults.buttonColors(
//////////////                            containerColor = HotPink,
//////////////                            contentColor = Color.White
//////////////                        ),
//////////////                        shape = RoundedCornerShape(12.dp),
//////////////                        enabled = cartItems.isNotEmpty(),
//////////////                        elevation = ButtonDefaults.buttonElevation(
//////////////                            defaultElevation = 8.dp,
//////////////                            pressedElevation = 4.dp
//////////////                        )
//////////////                    ) {
//////////////                        Text(
//////////////                            "Book Appointment",
//////////////                            fontWeight = FontWeight.Bold,
//////////////                            fontSize = 16.sp
//////////////                        )
//////////////                    }
//////////////                }
//////////////            }
//////////////        }
//////////////    ) { innerPadding ->
//////////////        Box(modifier = Modifier.fillMaxSize()) {
//////////////            Box(
//////////////                modifier = Modifier
//////////////                    .fillMaxSize()
//////////////                    .background(
//////////////                        brush = Brush.verticalGradient(
//////////////                            colors = listOf(Pink10, Color.White),
//////////////                            startY = 0f,
//////////////                            endY = 500f
//////////////                        )
//////////////                    )
//////////////            )
//////////////
//////////////            LazyColumn(
//////////////                modifier = Modifier
//////////////                    .padding(innerPadding)
//////////////                    .fillMaxSize()
//////////////            ) {
//////////////                if (cartItems.isEmpty()) {
//////////////                    item {
//////////////                        EmptyCartView(navController)
//////////////                    }
//////////////                } else {
//////////////                    item {
//////////////                        Text(
//////////////                            text = "Scheduled Services",
//////////////                            style = MaterialTheme.typography.titleMedium,
//////////////                            fontWeight = FontWeight.Bold,
//////////////                            color = HotPinkDark,
//////////////                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//////////////                        )
//////////////                    }
//////////////
//////////////                    items(cartItems) { cartItem ->
//////////////                        CartItemCard(
//////////////                            cartItem = cartItem,
//////////////                            onQuantityChange = { newQuantity ->
//////////////                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
//////////////                            },
//////////////                            onRemove = {
//////////////                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
//////////////                            }
//////////////                        )
//////////////                    }
//////////////                }
//////////////            }
//////////////        }
//////////////    }
//////////////
//////////////    // Booking Confirmation Dialog
//////////////    if (showBookingDialog) {
//////////////        BookingConfirmationDialog(
//////////////            cartItems = cartItems,
//////////////            subtotal = subtotal,
//////////////            discount = discount,
//////////////            total = total,
//////////////            onDismiss = { showBookingDialog = false },
//////////////            onConfirm = { customerName, phoneNumber, address, date, time ->
//////////////                sendWhatsAppBooking(
//////////////                    context = context,
//////////////                    whatsappNumber = whatsappNumber,
//////////////                    cartItems = cartItems,
//////////////                    subtotal = subtotal,
//////////////                    discount = discount,
//////////////                    total = total,
//////////////                    coupon = selectedCoupon,
//////////////                    customerName = customerName,
//////////////                    phoneNumber = phoneNumber,
//////////////                    address = address,
//////////////                    date = date,
//////////////                    time = time
//////////////                )
//////////////                showBookingDialog = false
//////////////            }
//////////////        )
//////////////    }
//////////////}
//////////////
//////////////@Composable
//////////////private fun BookingConfirmationDialog(
//////////////    cartItems: List<CartItem>,
//////////////    subtotal: Double,
//////////////    discount: Double,
//////////////    total: Double,
//////////////    onDismiss: () -> Unit,
//////////////    onConfirm: (String, String, String, String, String) -> Unit
//////////////) {
//////////////    var customerName by remember { mutableStateOf("") }
//////////////    var phoneNumber by remember { mutableStateOf("") }
//////////////    var address by remember { mutableStateOf("") }
//////////////    var date by remember { mutableStateOf("") }
//////////////    var time by remember { mutableStateOf("") }
//////////////    val context = LocalContext.current
//////////////
//////////////    AlertDialog(
//////////////        onDismissRequest = onDismiss,
//////////////        title = {
//////////////            Text(
//////////////                "Book Appointment",
//////////////                style = MaterialTheme.typography.titleLarge,
//////////////                color = HotPinkDark
//////////////            )
//////////////        },
//////////////        text = {
//////////////            Column(
//////////////                modifier = Modifier.verticalScroll(rememberScrollState())
//////////////            ) {
//////////////                // Customer Information Inputs
//////////////                OutlinedTextField(
//////////////                    value = customerName,
//////////////                    onValueChange = { customerName = it },
//////////////                    label = { Text("Customer Name") },
//////////////                    modifier = Modifier.fillMaxWidth(),
//////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////                        focusedBorderColor = HotPink,
//////////////                        focusedLabelColor = HotPink
//////////////                    )
//////////////                )
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                OutlinedTextField(
//////////////                    value = phoneNumber,
//////////////                    onValueChange = { phoneNumber = it },
//////////////                    label = { Text("Phone Number") },
//////////////                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//////////////                    modifier = Modifier.fillMaxWidth(),
//////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////                        focusedBorderColor = HotPink,
//////////////                        focusedLabelColor = HotPink
//////////////                    )
//////////////                )
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                OutlinedTextField(
//////////////                    value = address,
//////////////                    onValueChange = { address = it },
//////////////                    label = { Text("Address") },
//////////////                    modifier = Modifier.fillMaxWidth(),
//////////////                    colors = OutlinedTextFieldDefaults.colors(
//////////////                        focusedBorderColor = HotPink,
//////////////                        focusedLabelColor = HotPink
//////////////                    )
//////////////                )
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                Row(
//////////////                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//////////////                ) {
//////////////                    OutlinedTextField(
//////////////                        value = date,
//////////////                        onValueChange = { date = it },
//////////////                        label = { Text("Date") },
//////////////                        modifier = Modifier.weight(1f),
//////////////                        colors = OutlinedTextFieldDefaults.colors(
//////////////                            focusedBorderColor = HotPink,
//////////////                            focusedLabelColor = HotPink
//////////////                        ),
//////////////                        readOnly = true,
//////////////                        trailingIcon = {
//////////////                            IconButton(onClick = {
//////////////                                showDatePicker(context) { selectedDate ->
//////////////                                    date = selectedDate
//////////////                                }
//////////////                            }) {
//////////////                                Icon(
//////////////                                    Icons.Default.CalendarToday,
//////////////                                    contentDescription = "Pick Date",
//////////////                                    tint = HotPink
//////////////                                )
//////////////                            }
//////////////                        }
//////////////                    )
//////////////
//////////////                    OutlinedTextField(
//////////////                        value = time,
//////////////                        onValueChange = { time = it },
//////////////                        label = { Text("Time") },
//////////////                        modifier = Modifier.weight(1f),
//////////////                        colors = OutlinedTextFieldDefaults.colors(
//////////////                            focusedBorderColor = HotPink,
//////////////                            focusedLabelColor = HotPink
//////////////                        ),
//////////////                        readOnly = true,
//////////////                        trailingIcon = {
//////////////                            IconButton(onClick = {
//////////////                                showTimePicker(context) { selectedTime ->
//////////////                                    time = selectedTime
//////////////                                }
//////////////                            }) {
//////////////                                Icon(
//////////////                                    Icons.Default.AccessTime,
//////////////                                    contentDescription = "Pick Time",
//////////////                                    tint = HotPink
//////////////                                )
//////////////                            }
//////////////                        }
//////////////                    )
//////////////                }
//////////////
//////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////
//////////////                // Services summary
//////////////                Text(
//////////////                    "Services Booked:",
//////////////                    style = MaterialTheme.typography.bodyLarge,
//////////////                    fontWeight = FontWeight.Bold,
//////////////                    color = HotPinkDark
//////////////                )
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                cartItems.forEach { item ->
//////////////                    Text(
//////////////                        "- ${item.name} (₹${item.price}) - ${item.duration}",
//////////////                        color = Color.Gray
//////////////                    )
//////////////                }
//////////////
//////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////
//////////////                PriceRow("Subtotal:", subtotal)
//////////////
//////////////                if (discount > 0) {
//////////////                    PriceRow("Discount:", -discount, HotPink)
//////////////                }
//////////////
//////////////                Divider(modifier = Modifier.padding(vertical = 8.dp))
//////////////
//////////////                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)
//////////////
//////////////                Spacer(modifier = Modifier.height(16.dp))
//////////////
//////////////                Text(
//////////////                    "A WhatsApp message will be sent to confirm your appointment",
//////////////                    style = MaterialTheme.typography.bodySmall,
//////////////                    color = Color.Gray
//////////////                )
//////////////            }
//////////////        },
//////////////        confirmButton = {
//////////////            Button(
//////////////                onClick = {
//////////////                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
//////////////                        address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
//////////////                        onConfirm(customerName, phoneNumber, address, date, time)
//////////////                    } else {
//////////////                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//////////////                    }
//////////////                },
//////////////                colors = ButtonDefaults.buttonColors(
//////////////                    containerColor = HotPink,
//////////////                    contentColor = Color.White
//////////////                ),
//////////////                shape = RoundedCornerShape(12.dp)
//////////////            ) {
//////////////                Text("Confirm & Send WhatsApp")
//////////////            }
//////////////        },
//////////////        dismissButton = {
//////////////            TextButton(
//////////////                onClick = onDismiss,
//////////////                shape = RoundedCornerShape(12.dp)
//////////////            ) {
//////////////                Text("Cancel", color = HotPink)
//////////////            }
//////////////        },
//////////////        containerColor = Color.White,
//////////////        shape = RoundedCornerShape(24.dp)
//////////////    )
//////////////}
//////////////
//////////////private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
//////////////    val calendar = Calendar.getInstance()
//////////////    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//////////////        calendar.set(Calendar.YEAR, year)
//////////////        calendar.set(Calendar.MONTH, month)
//////////////        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//////////////
//////////////        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//////////////        onDateSelected(sdf.format(calendar.time))
//////////////    }
//////////////
//////////////    DatePickerDialog(
//////////////        context,
//////////////        dateSetListener,
//////////////        calendar.get(Calendar.YEAR),
//////////////        calendar.get(Calendar.MONTH),
//////////////        calendar.get(Calendar.DAY_OF_MONTH)
//////////////    ).apply {
//////////////        datePicker.minDate = System.currentTimeMillis() - 1000
//////////////        show()
//////////////    }
//////////////}
//////////////
//////////////private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
//////////////    val calendar = Calendar.getInstance()
//////////////    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//////////////        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//////////////        calendar.set(Calendar.MINUTE, minute)
//////////////
//////////////        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//////////////        onTimeSelected(sdf.format(calendar.time))
//////////////    }
//////////////
//////////////    TimePickerDialog(
//////////////        context,
//////////////        timeSetListener,
//////////////        calendar.get(Calendar.HOUR_OF_DAY),
//////////////        calendar.get(Calendar.MINUTE),
//////////////        false
//////////////    ).show()
//////////////}
//////////////
//////////////private fun sendWhatsAppBooking(
//////////////    context: Context,
//////////////    whatsappNumber: String,
//////////////    cartItems: List<CartItem>,
//////////////    subtotal: Double,
//////////////    discount: Double,
//////////////    total: Double,
//////////////    coupon: Coupon?,
//////////////    customerName: String,
//////////////    phoneNumber: String,
//////////////    address: String,
//////////////    date: String,
//////////////    time: String
//////////////) {
//////////////    val message = buildString {
//////////////        append("🌟 *Beauty Parlor Booking* 🌟\n\n")
//////////////
//////////////        // Customer information
//////////////        append("*Customer Details:*\n")
//////////////        append("Name: $customerName\n")
//////////////        append("Phone: $phoneNumber\n")
//////////////        append("Address: $address\n")
//////////////        append("Appointment Date: $date\n")
//////////////        append("Appointment Time: $time\n\n")
//////////////
//////////////        // Services list
//////////////        append("*Services Booked:*\n")
//////////////        cartItems.forEachIndexed { index, item ->
//////////////            append("${index + 1}. ${item.name} - ₹${item.price} - ${item.duration}")
//////////////            if (item.quantity > 1) append(" (Qty: ${item.quantity})")
//////////////            append("\n")
//////////////        }
//////////////
//////////////        // Payment summary
//////////////        append("\n*Payment Summary:*\n")
//////////////        append("Subtotal: ₹${"%.2f".format(subtotal)}\n")
//////////////        if (discount > 0) {
//////////////            append("Discount: -₹${"%.2f".format(discount)}")
//////////////            coupon?.let { append(" (Coupon: ${it.code})") }
//////////////            append("\n")
//////////////        }
//////////////        append("Total: ₹${"%.2f".format(total)}\n\n")
//////////////
//////////////        // Calculate total duration
//////////////        val totalDuration = calculateTotalDuration(cartItems)
//////////////        append("⏱️ *Total Duration:* ${totalDuration} minutes\n\n")
//////////////
//////////////        // Closing message
//////////////        append("Please confirm my appointment. Thank you! 💖")
//////////////    }
//////////////
//////////////    try {
//////////////        val url = "https://wa.me/$whatsappNumber?text=${URLEncoder.encode(message, "UTF-8")}"
//////////////        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//////////////            setPackage("com.whatsapp")
//////////////        }
//////////////        context.startActivity(intent)
//////////////    } catch (e: ActivityNotFoundException) {
//////////////        Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
//////////////    }
//////////////}
//////////////
//////////////private fun calculateTotalDuration(cartItems: List<CartItem>): Int {
//////////////    return cartItems.sumOf { item ->
//////////////        // Extract duration from the duration string (e.g., "30 min" -> 30)
//////////////        val durationString = item.duration ?: "0 min"
//////////////        val durationValue = durationString.replace("min", "").trim().toIntOrNull() ?: 0
//////////////        durationValue * item.quantity
//////////////    }
//////////////}
//////////////
//////////////@Composable
//////////////private fun EmptyCartView(navController: NavController) {
//////////////    Column(
//////////////        modifier = Modifier
//////////////            .fillMaxSize()
//////////////            .padding(32.dp),
//////////////        horizontalAlignment = Alignment.CenterHorizontally,
//////////////        verticalArrangement = Arrangement.Center
//////////////    ) {
//////////////        Icon(
//////////////            imageVector = Icons.Default.ShoppingCart,
//////////////            contentDescription = "Empty Cart",
//////////////            tint = Pink40,
//////////////            modifier = Modifier.size(64.dp)
//////////////        )
//////////////        Spacer(modifier = Modifier.height(16.dp))
//////////////        Text(
//////////////            "Your cart is empty",
//////////////            style = MaterialTheme.typography.titleLarge,
//////////////            color = HotPinkDark
//////////////        )
//////////////        Spacer(modifier = Modifier.height(8.dp))
//////////////        Text(
//////////////            "Browse our services and add something special",
//////////////            style = MaterialTheme.typography.bodyMedium,
//////////////            color = Pink80,
//////////////            textAlign = TextAlign.Center
//////////////        )
//////////////        Spacer(modifier = Modifier.height(24.dp))
//////////////        Button(
//////////////            onClick = {
//////////////                navController.navigate("services") {
//////////////                    popUpTo("home") { saveState = true }
//////////////                    launchSingleTop = true
//////////////                    restoreState = true
//////////////                }
//////////////            },
//////////////            colors = ButtonDefaults.buttonColors(
//////////////                containerColor = HotPink,
//////////////                contentColor = Color.White
//////////////            ),
//////////////            shape = RoundedCornerShape(12.dp)
//////////////        ) {
//////////////            Text("Explore Services")
//////////////        }
//////////////    }
//////////////}
//////////////
//////////////@Composable
//////////////private fun CartItemCard(
//////////////    cartItem: CartItem,
//////////////    onQuantityChange: (Int) -> Unit,
//////////////    onRemove: () -> Unit
//////////////) {
//////////////    Card(
//////////////        modifier = Modifier
//////////////            .padding(horizontal = 16.dp, vertical = 8.dp)
//////////////            .fillMaxWidth(),
//////////////        shape = RoundedCornerShape(20.dp),
//////////////        colors = CardDefaults.cardColors(containerColor = Color.White),
//////////////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//////////////    ) {
//////////////        Row(modifier = Modifier.padding(16.dp)) {
//////////////            Box(
//////////////                modifier = Modifier
//////////////                    .size(100.dp)
//////////////                    .clip(RoundedCornerShape(12.dp))
//////////////                    .background(Pink10)
//////////////            ) {
//////////////                if (cartItem.imageUrl.isNotEmpty()) {
//////////////                    AsyncImage(
//////////////                        model = cartItem.imageUrl,
//////////////                        contentDescription = cartItem.name,
//////////////                        contentScale = ContentScale.Crop,
//////////////                        modifier = Modifier.fillMaxSize()
//////////////                    )
//////////////                } else {
//////////////                    Icon(
//////////////                        imageVector = Icons.Default.Spa,
//////////////                        contentDescription = "Service Image",
//////////////                        tint = HotPink,
//////////////                        modifier = Modifier
//////////////                            .size(60.dp)
//////////////                            .align(Alignment.Center)
//////////////                    )
//////////////                }
//////////////            }
//////////////
//////////////            Spacer(modifier = Modifier.width(16.dp))
//////////////
//////////////            Column(modifier = Modifier.weight(1f)) {
//////////////                Text(
//////////////                    cartItem.name,
//////////////                    style = MaterialTheme.typography.titleSmall,
//////////////                    fontWeight = FontWeight.Bold,
//////////////                    color = HotPinkDark
//////////////                )
//////////////
//////////////                Spacer(modifier = Modifier.height(4.dp))
//////////////
//////////////                // Display duration
//////////////                cartItem.duration?.let {
//////////////                    Text(
//////////////                        it,
//////////////                        style = MaterialTheme.typography.labelSmall,
//////////////                        color = Pink80
//////////////                    )
//////////////                }
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                Row(verticalAlignment = Alignment.CenterVertically) {
//////////////                    Text(
//////////////                        "₹${cartItem.price}",
//////////////                        style = MaterialTheme.typography.bodyMedium,
//////////////                        fontWeight = FontWeight.Bold,
//////////////                        color = HotPink
//////////////                    )
//////////////
//////////////                    if (cartItem.originalPrice != null) {
//////////////                        Spacer(modifier = Modifier.width(8.dp))
//////////////                        Text(
//////////////                            "₹${cartItem.originalPrice}",
//////////////                            style = MaterialTheme.typography.labelSmall,
//////////////                            color = Color.Gray,
//////////////                            textDecoration = TextDecoration.LineThrough
//////////////                        )
//////////////                    }
//////////////                }
//////////////
//////////////                Spacer(modifier = Modifier.height(8.dp))
//////////////
//////////////                Row(
//////////////                    verticalAlignment = Alignment.CenterVertically,
//////////////                    horizontalArrangement = Arrangement.SpaceBetween,
//////////////                    modifier = Modifier.fillMaxWidth()
//////////////                ) {
//////////////                    // Quantity selector
//////////////                    QuantitySelector(
//////////////                        quantity = cartItem.quantity,
//////////////                        onQuantityChange = onQuantityChange
//////////////                    )
//////////////
//////////////                    // Remove button
//////////////                    IconButton(
//////////////                        onClick = onRemove,
//////////////                        modifier = Modifier
//////////////                            .size(32.dp)
//////////////                            .clip(CircleShape)
//////////////                    ) {
//////////////                        Icon(
//////////////                            imageVector = Icons.Default.Delete,
//////////////                            contentDescription = "Remove",
//////////////                            tint = HotPink
//////////////                        )
//////////////                    }
//////////////                }
//////////////            }
//////////////        }
//////////////    }
//////////////}
//////////////
//////////////@Composable
//////////////private fun QuantitySelector(
//////////////    quantity: Int,
//////////////    onQuantityChange: (Int) -> Unit
//////////////) {
//////////////    Surface(
//////////////        shape = RoundedCornerShape(12.dp),
//////////////        border = BorderStroke(1.dp, Pink20),
//////////////        modifier = Modifier.height(32.dp)
//////////////    ) {
//////////////        Row(
//////////////            verticalAlignment = Alignment.CenterVertically,
//////////////            horizontalArrangement = Arrangement.Center
//////////////        ) {
//////////////            IconButton(
//////////////                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
//////////////                modifier = Modifier.size(32.dp)
//////////////            ) {
//////////////                Icon(
//////////////                    imageVector = Icons.Default.Remove,
//////////////                    contentDescription = "Decrease",
//////////////                    tint = if (quantity > 1) HotPink else Color.LightGray
//////////////                )
//////////////            }
//////////////
//////////////            Text(
//////////////                text = quantity.toString(),
//////////////                style = MaterialTheme.typography.bodyMedium,
//////////////                fontWeight = FontWeight.Bold,
//////////////                color = HotPinkDark,
//////////////                modifier = Modifier.padding(horizontal = 8.dp)
//////////////            )
//////////////
//////////////            IconButton(
//////////////                onClick = { onQuantityChange(quantity + 1) },
//////////////                modifier = Modifier.size(32.dp)
//////////////            ) {
//////////////                Icon(
//////////////                    imageVector = Icons.Default.Add,
//////////////                    contentDescription = "Increase",
//////////////                    tint = HotPink
//////////////                )
//////////////            }
//////////////        }
//////////////    }
//////////////}
//////////////
//////////////@Composable
//////////////private fun PriceRow(
//////////////    label: String,
//////////////    amount: Double,
//////////////    color: Color = Color.Gray,
//////////////    fontWeight: FontWeight = FontWeight.Normal
//////////////) {
//////////////    Row(
//////////////        modifier = Modifier.fillMaxWidth(),
//////////////        horizontalArrangement = Arrangement.SpaceBetween
//////////////    ) {
//////////////        Text(
//////////////            label,
//////////////            style = MaterialTheme.typography.bodyMedium,
//////////////            color = color,
//////////////            fontWeight = fontWeight
//////////////        )
//////////////        Text(
//////////////            "₹${"%.2f".format(amount)}",
//////////////            style = MaterialTheme.typography.bodyMedium,
//////////////            color = color,
//////////////            fontWeight = fontWeight
//////////////        )
//////////////    }
//////////////}
//////////////
//////////////private fun calculateSubtotal(items: List<CartItem>): Double {
//////////////    return items.sumOf { it.price * it.quantity }
//////////////}
//////////////
//////////////private fun calculateDiscount(items: List<CartItem>, coupon: Coupon?): Double {
//////////////    if (coupon == null) return 0.0
//////////////    val subtotal = calculateSubtotal(items)
//////////////    return (subtotal * coupon.discountPercentage / 100).coerceAtMost(
//////////////        coupon.maxDiscount ?: Double.MAX_VALUE
//////////////    )
//////////////}
//////////////
//////////////
//////////////
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
//////import android.app.DatePickerDialog
//////import android.app.TimePickerDialog
//////import android.content.ActivityNotFoundException
//////import android.content.Context
//////import android.content.Intent
//////import android.net.Uri
//////import android.util.Log
//////import android.widget.Toast
//////import androidx.compose.foundation.BorderStroke
//////import androidx.compose.foundation.background
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
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.items
//////import androidx.compose.foundation.rememberScrollState
//////import androidx.compose.foundation.shape.CircleShape
//////import androidx.compose.foundation.shape.RoundedCornerShape
//////import androidx.compose.foundation.text.KeyboardOptions
//////import androidx.compose.foundation.verticalScroll
//////import androidx.compose.material.icons.Icons
//////import androidx.compose.material.icons.filled.AccessTime
//////import androidx.compose.material.icons.filled.Add
//////import androidx.compose.material.icons.filled.ArrowBack
//////import androidx.compose.material.icons.filled.CalendarToday
//////import androidx.compose.material.icons.filled.CheckCircle
//////import androidx.compose.material.icons.filled.Delete
//////import androidx.compose.material.icons.filled.Remove
//////import androidx.compose.material.icons.filled.ShoppingCart
//////import androidx.compose.material.icons.filled.Spa
//////import androidx.compose.material3.AlertDialog
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
//////import androidx.compose.material3.MaterialTheme
//////import androidx.compose.material3.OutlinedButton
//////import androidx.compose.material3.OutlinedTextField
//////import androidx.compose.material3.OutlinedTextFieldDefaults
//////import androidx.compose.material3.Scaffold
//////import androidx.compose.material3.Surface
//////import androidx.compose.material3.Text
//////import androidx.compose.material3.TextButton
//////import androidx.compose.material3.TopAppBarDefaults
//////import androidx.compose.runtime.Composable
//////import androidx.compose.runtime.LaunchedEffect
//////import androidx.compose.runtime.collectAsState
//////import androidx.compose.runtime.getValue
//////import androidx.compose.runtime.mutableStateOf
//////import androidx.compose.runtime.remember
//////import androidx.compose.runtime.setValue
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.clip
//////import androidx.compose.ui.draw.shadow
//////import androidx.compose.ui.graphics.Brush
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.layout.ContentScale
//////import androidx.compose.ui.platform.LocalContext
//////import androidx.compose.ui.text.font.FontWeight
//////import androidx.compose.ui.text.input.KeyboardType
//////import androidx.compose.ui.text.style.TextAlign
//////import androidx.compose.ui.text.style.TextDecoration
//////import androidx.compose.ui.unit.dp
//////import androidx.compose.ui.unit.sp
//////import androidx.lifecycle.viewmodel.compose.viewModel
//////import androidx.navigation.NavController
//////import coil.compose.AsyncImage
//////import com.beauty.parler.model.CartItem
//////import com.beauty.parler.shared.CartRepository
//////import com.beauty.parler.shared.CartViewModel
//////import com.beauty.parler.shared.CartViewModelFactory
//////import com.beauty.parler.ui.theme.HotPink
//////import com.beauty.parler.ui.theme.HotPinkDark
//////import com.beauty.parler.ui.theme.Pink10
//////import com.beauty.parler.ui.theme.Pink20
//////import com.beauty.parler.ui.theme.Pink40
//////import com.beauty.parler.ui.theme.Pink80
//////import com.google.firebase.firestore.FirebaseFirestore
//////import kotlinx.coroutines.tasks.await
//////import java.net.URLEncoder
//////import java.text.SimpleDateFormat
//////import java.util.Calendar
//////import java.util.Locale
//////
//////data class Coupon(
//////    val id: String = "",
//////    val code: String = "",
//////    val discountPercentage: Int = 0,
//////    val title: String = "",
//////    val description: String = "",
//////    val validUntil: Long = 0L,
//////    val formattedDate: String = "",
//////    val maxDiscount: Double? = null
//////)
//////
//////@OptIn(ExperimentalMaterial3Api::class)
//////@Composable
//////fun CartScreen(navController: NavController, viewModel: CartViewModel) {
//////    val context = LocalContext.current
//////    val cartRepository = remember { CartRepository(context) }
//////    val factory = remember { CartViewModelFactory(cartRepository) }
//////    val cartViewModel: CartViewModel = viewModel(factory = factory)
//////    val cartItems by cartViewModel.cartItems.collectAsState()
//////    var showCouponDialog by remember { mutableStateOf(false) }
//////    var couponCode by remember { mutableStateOf("") }
//////    var selectedCoupon by remember { mutableStateOf<Coupon?>(null) }
//////    var showBookingDialog by remember { mutableStateOf(false) }
//////    var availableCoupons by remember { mutableStateOf<List<Coupon>>(emptyList()) }
//////    var isLoadingCoupons by remember { mutableStateOf(false) }
//////    var couponError by remember { mutableStateOf<String?>(null) }
//////
//////    val whatsappNumber = "+917808388674"
//////    val db = FirebaseFirestore.getInstance()
//////
//////    // Load coupons from Firestore
//////    LaunchedEffect(Unit) {
//////        isLoadingCoupons = true
//////        try {
//////            val result = db.collection("offers")
//////                .whereGreaterThanOrEqualTo("validDate", System.currentTimeMillis())
//////                .get()
//////                .await()
//////
//////            availableCoupons = result.documents.mapNotNull { doc ->
//////                try {
//////                    Coupon(
//////                        id = doc.id,
//////                        code = doc.getString("useCode") ?: "",
//////                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
//////                        title = doc.getString("title") ?: "",
//////                        description = doc.getString("description") ?: "",
//////                        validUntil = doc.getLong("validDate") ?: 0L,
//////                        formattedDate = doc.getString("formattedDate") ?: "",
//////                        maxDiscount = doc.getDouble("maxDiscount")
//////                    )
//////                } catch (e: Exception) {
//////                    Log.e("CartScreen", "Error parsing coupon ${doc.id}", e)
//////                    null
//////                }
//////            }
//////        } catch (e: Exception) {
//////            couponError = "Failed to load coupons. Please try again."
//////            Log.e("CartScreen", "Error loading coupons", e)
//////        } finally {
//////            isLoadingCoupons = false
//////        }
//////    }
//////
//////    // Calculate prices
//////    val subtotal = calculateSubtotal(cartItems)
//////    val discount = calculateDiscount(cartItems, selectedCoupon)
//////    val total = subtotal - discount
//////
//////    Scaffold(
//////        topBar = {
//////            CenterAlignedTopAppBar(
//////                title = {
//////                    Text(
//////                        "Your Cart",
//////                        style = MaterialTheme.typography.titleLarge,
//////                        fontWeight = FontWeight.Bold,
//////                        color = HotPinkDark
//////                    )
//////                },
//////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//////                    containerColor = Color.White
//////                ),
//////                navigationIcon = {
//////                    IconButton(onClick = { navController.popBackStack() }) {
//////                        Icon(
//////                            imageVector = Icons.Default.ArrowBack,
//////                            contentDescription = "Back",
//////                            tint = HotPink
//////                        )
//////                    }
//////                }
//////            )
//////        },
//////        bottomBar = {
//////            Surface(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .shadow(elevation = 16.dp),
//////                color = Color.White
//////            ) {
//////                Column(
//////                    modifier = Modifier.padding(16.dp)
//////                ) {
//////                    PriceRow(
//////                        label = "Subtotal (${cartItems.size} items):",
//////                        amount = subtotal
//////                    )
//////
//////                    if (selectedCoupon != null) {
//////                        PriceRow(
//////                            label = "Discount (${selectedCoupon?.code}):",
//////                            amount = -discount,
//////                            color = HotPink
//////                        )
//////                    }
//////
//////                    Divider(modifier = Modifier.padding(vertical = 8.dp))
//////
//////                    PriceRow(
//////                        label = "Total Amount:",
//////                        amount = total,
//////                        color = HotPinkDark,
//////                        fontWeight = FontWeight.Bold
//////                    )
//////
//////                    Spacer(modifier = Modifier.height(16.dp))
//////
//////                    Button(
//////                        onClick = { showBookingDialog = true },
//////                        modifier = Modifier
//////                            .fillMaxWidth()
//////                            .height(50.dp),
//////                        colors = ButtonDefaults.buttonColors(
//////                            containerColor = HotPink,
//////                            contentColor = Color.White
//////                        ),
//////                        shape = RoundedCornerShape(12.dp),
//////                        enabled = cartItems.isNotEmpty(),
//////                        elevation = ButtonDefaults.buttonElevation(
//////                            defaultElevation = 8.dp,
//////                            pressedElevation = 4.dp
//////                        )
//////                    ) {
//////                        Text(
//////                            "Book Appointment",
//////                            fontWeight = FontWeight.Bold,
//////                            fontSize = 16.sp
//////                        )
//////                    }
//////                }
//////            }
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
//////            LazyColumn(
//////                modifier = Modifier
//////                    .padding(innerPadding)
//////                    .fillMaxSize()
//////            ) {
//////                if (cartItems.isEmpty()) {
//////                    item {
//////                        EmptyCartView(navController)
//////                    }
//////                } else {
//////                    item {
//////                        Text(
//////                            text = "Scheduled Services",
//////                            style = MaterialTheme.typography.titleMedium,
//////                            fontWeight = FontWeight.Bold,
//////                            color = HotPinkDark,
//////                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//////                        )
//////                    }
//////
//////                    items(cartItems) { cartItem ->
//////                        CartItemCard(
//////                            cartItem = cartItem,
//////                            onQuantityChange = { newQuantity ->
//////                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
//////                            },
//////                            onPersonsChange = { newPersons ->
//////                                cartItem.id?.let { cartViewModel.updatePersons(it, newPersons) }
//////                            },
//////                            onRemove = {
//////                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
//////                            }
//////                        )
//////                    }
//////                }
//////            }
//////        }
//////    }
//////
//////    // Booking Confirmation Dialog
//////    if (showBookingDialog) {
//////        BookingConfirmationDialog(
//////            cartItems = cartItems,
//////            subtotal = subtotal,
//////            discount = discount,
//////            total = total,
//////            onDismiss = { showBookingDialog = false },
//////            onConfirm = { customerName, phoneNumber, address, date, time ->
//////                sendWhatsAppBooking(
//////                    context = context,
//////                    whatsappNumber = whatsappNumber,
//////                    cartItems = cartItems,
//////                    subtotal = subtotal,
//////                    discount = discount,
//////                    total = total,
//////                    coupon = selectedCoupon,
//////                    customerName = customerName,
//////                    phoneNumber = phoneNumber,
//////                    address = address,
//////                    date = date,
//////                    time = time
//////                )
//////                showBookingDialog = false
//////            }
//////        )
//////    }
//////}
//////
//////@Composable
//////private fun BookingConfirmationDialog(
//////    cartItems: List<CartItem>,
//////    subtotal: Double,
//////    discount: Double,
//////    total: Double,
//////    onDismiss: () -> Unit,
//////    onConfirm: (String, String, String, String, String) -> Unit
//////) {
//////    var customerName by remember { mutableStateOf("") }
//////    var phoneNumber by remember { mutableStateOf("") }
//////    var address by remember { mutableStateOf("") }
//////    var date by remember { mutableStateOf("") }
//////    var time by remember { mutableStateOf("") }
//////    val context = LocalContext.current
//////
//////    AlertDialog(
//////        onDismissRequest = onDismiss,
//////        title = {
//////            Text(
//////                "Book Appointment",
//////                style = MaterialTheme.typography.titleLarge,
//////                color = HotPinkDark
//////            )
//////        },
//////        text = {
//////            Column(
//////                modifier = Modifier.verticalScroll(rememberScrollState())
//////            ) {
//////                // Customer Information Inputs
//////                OutlinedTextField(
//////                    value = customerName,
//////                    onValueChange = { customerName = it },
//////                    label = { Text("Customer Name") },
//////                    modifier = Modifier.fillMaxWidth(),
//////                    colors = OutlinedTextFieldDefaults.colors(
//////                        focusedBorderColor = HotPink,
//////                        focusedLabelColor = HotPink
//////                    )
//////                )
//////
//////                Spacer(modifier = Modifier.height(8.dp))
//////
//////                OutlinedTextField(
//////                    value = phoneNumber,
//////                    onValueChange = { phoneNumber = it },
//////                    label = { Text("Phone Number") },
//////                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//////                    modifier = Modifier.fillMaxWidth(),
//////                    colors = OutlinedTextFieldDefaults.colors(
//////                        focusedBorderColor = HotPink,
//////                        focusedLabelColor = HotPink
//////                    )
//////                )
//////
//////                Spacer(modifier = Modifier.height(8.dp))
//////
//////                OutlinedTextField(
//////                    value = address,
//////                    onValueChange = { address = it },
//////                    label = { Text("Address") },
//////                    modifier = Modifier.fillMaxWidth(),
//////                    colors = OutlinedTextFieldDefaults.colors(
//////                        focusedBorderColor = HotPink,
//////                        focusedLabelColor = HotPink
//////                    )
//////                )
//////
//////                Spacer(modifier = Modifier.height(8.dp))
//////
//////                Row(
//////                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//////                ) {
//////                    OutlinedTextField(
//////                        value = date,
//////                        onValueChange = { date = it },
//////                        label = { Text("Date") },
//////                        modifier = Modifier.weight(1f),
//////                        colors = OutlinedTextFieldDefaults.colors(
//////                            focusedBorderColor = HotPink,
//////                            focusedLabelColor = HotPink
//////                        ),
//////                        readOnly = true,
//////                        trailingIcon = {
//////                            IconButton(onClick = {
//////                                showDatePicker(context) { selectedDate ->
//////                                    date = selectedDate
//////                                }
//////                            }) {
//////                                Icon(
//////                                    Icons.Default.CalendarToday,
//////                                    contentDescription = "Pick Date",
//////                                    tint = HotPink
//////                                )
//////                            }
//////                        }
//////                    )
//////
//////                    OutlinedTextField(
//////                        value = time,
//////                        onValueChange = { time = it },
//////                        label = { Text("Time") },
//////                        modifier = Modifier.weight(1f),
//////                        colors = OutlinedTextFieldDefaults.colors(
//////                            focusedBorderColor = HotPink,
//////                            focusedLabelColor = HotPink
//////                        ),
//////                        readOnly = true,
//////                        trailingIcon = {
//////                            IconButton(onClick = {
//////                                showTimePicker(context) { selectedTime ->
//////                                    time = selectedTime
//////                                }
//////                            }) {
//////                                Icon(
//////                                    Icons.Default.AccessTime,
//////                                    contentDescription = "Pick Time",
//////                                    tint = HotPink
//////                                )
//////                            }
//////                        }
//////                    )
//////                }
//////
//////                Spacer(modifier = Modifier.height(16.dp))
//////
//////                // Services summary
//////                Text(
//////                    "Services Booked:",
//////                    style = MaterialTheme.typography.bodyLarge,
//////                    fontWeight = FontWeight.Bold,
//////                    color = HotPinkDark
//////                )
//////
//////                Spacer(modifier = Modifier.height(8.dp))
//////
//////                cartItems.forEach { item ->
//////                    Text(
//////                        "- ${item.name} (₹${item.price}) - ${item.duration} - ${item.persons} person(s)",
//////                        color = Color.Gray
//////                    )
//////                }
//////
//////                Spacer(modifier = Modifier.height(16.dp))
//////
//////                PriceRow("Subtotal:", subtotal)
//////
//////                if (discount > 0) {
//////                    PriceRow("Discount:", -discount, HotPink)
//////                }
//////
//////                Divider(modifier = Modifier.padding(vertical = 8.dp))
//////
//////                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)
//////
//////                Spacer(modifier = Modifier.height(16.dp))
//////
//////                Text(
//////                    "A WhatsApp message will be sent to confirm your appointment",
//////                    style = MaterialTheme.typography.bodySmall,
//////                    color = Color.Gray
//////                )
//////            }
//////        },
//////        confirmButton = {
//////            Button(
//////                onClick = {
//////                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
//////                        address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
//////                        onConfirm(customerName, phoneNumber, address, date, time)
//////                    } else {
//////                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
//////                    }
//////                },
//////                colors = ButtonDefaults.buttonColors(
//////                    containerColor = HotPink,
//////                    contentColor = Color.White
//////                ),
//////                shape = RoundedCornerShape(12.dp)
//////            ) {
//////                Text("Confirm & Send WhatsApp")
//////            }
//////        },
//////        dismissButton = {
//////            TextButton(
//////                onClick = onDismiss,
//////                shape = RoundedCornerShape(12.dp)
//////            ) {
//////                Text("Cancel", color = HotPink)
//////            }
//////        },
//////        containerColor = Color.White,
//////        shape = RoundedCornerShape(24.dp)
//////    )
//////}
//////
//////private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
//////    val calendar = Calendar.getInstance()
//////    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//////        calendar.set(Calendar.YEAR, year)
//////        calendar.set(Calendar.MONTH, month)
//////        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//////
//////        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//////        onDateSelected(sdf.format(calendar.time))
//////    }
//////
//////    DatePickerDialog(
//////        context,
//////        dateSetListener,
//////        calendar.get(Calendar.YEAR),
//////        calendar.get(Calendar.MONTH),
//////        calendar.get(Calendar.DAY_OF_MONTH)
//////    ).apply {
//////        datePicker.minDate = System.currentTimeMillis() - 1000
//////        show()
//////    }
//////}
//////
//////private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
//////    val calendar = Calendar.getInstance()
//////    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//////        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//////        calendar.set(Calendar.MINUTE, minute)
//////
//////        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//////        onTimeSelected(sdf.format(calendar.time))
//////    }
//////
//////    TimePickerDialog(
//////        context,
//////        timeSetListener,
//////        calendar.get(Calendar.HOUR_OF_DAY),
//////        calendar.get(Calendar.MINUTE),
//////        false
//////    ).show()
//////}
//////
//////private fun sendWhatsAppBooking(
//////    context: Context,
//////    whatsappNumber: String,
//////    cartItems: List<CartItem>,
//////    subtotal: Double,
//////    discount: Double,
//////    total: Double,
//////    coupon: Coupon?,
//////    customerName: String,
//////    phoneNumber: String,
//////    address: String,
//////    date: String,
//////    time: String
//////) {
//////    val message = buildString {
//////        append("🌟 *Beauty Parlor Booking* 🌟\n\n")
//////
//////        // Customer information
//////        append("*Customer Details:*\n")
//////        append("Name: $customerName\n")
//////        append("Phone: $phoneNumber\n")
//////        append("Address: $address\n")
//////        append("Appointment Date: $date\n")
//////        append("Appointment Time: $time\n\n")
//////
//////        // Services list
//////        append("*Services Booked:*\n")
//////        cartItems.forEachIndexed { index, item ->
//////            append("${index + 1}. ${item.name} - ₹${item.price} - ${item.duration} - ${item.persons} person(s)")
//////            if (item.quantity > 1) append(" (Qty: ${item.quantity})")
//////            append("\n")
//////        }
//////
//////        // Payment summary
//////        append("\n*Payment Summary:*\n")
//////        append("Subtotal: ₹${"%.2f".format(subtotal)}\n")
//////        if (discount > 0) {
//////            append("Discount: -₹${"%.2f".format(discount)}")
//////            coupon?.let { append(" (Coupon: ${it.code})") }
//////            append("\n")
//////        }
//////        append("Total: ₹${"%.2f".format(total)}\n\n")
//////
//////        // Calculate total duration
//////        val totalDuration = calculateTotalDuration(cartItems)
//////        append("⏱️ *Total Duration:* ${totalDuration} minutes\n\n")
//////
//////        // Closing message
//////        append("Please confirm my appointment. Thank you! 💖")
//////    }
//////
//////    try {
//////        val url = "https://wa.me/$whatsappNumber?text=${URLEncoder.encode(message, "UTF-8")}"
//////        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//////            setPackage("com.whatsapp")
//////        }
//////        context.startActivity(intent)
//////    } catch (e: ActivityNotFoundException) {
//////        Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
//////    }
//////}
//////
//////private fun calculateTotalDuration(cartItems: List<CartItem>): Int {
//////    return cartItems.sumOf { item ->
//////        // Extract duration from the duration string (e.g., "30 min" -> 30)
//////        val durationString = item.duration ?: "0 min"
//////        val durationValue = durationString.replace("min", "").trim().toIntOrNull() ?: 0
//////        durationValue * item.quantity * item.persons
//////    }
//////}
//////
//////@Composable
//////private fun EmptyCartView(navController: NavController) {
//////    Column(
//////        modifier = Modifier
//////            .fillMaxSize()
//////            .padding(32.dp),
//////        horizontalAlignment = Alignment.CenterHorizontally,
//////        verticalArrangement = Arrangement.Center
//////    ) {
//////        Icon(
//////            imageVector = Icons.Default.ShoppingCart,
//////            contentDescription = "Empty Cart",
//////            tint = Pink40,
//////            modifier = Modifier.size(64.dp)
//////        )
//////        Spacer(modifier = Modifier.height(16.dp))
//////        Text(
//////            "Your cart is empty",
//////            style = MaterialTheme.typography.titleLarge,
//////            color = HotPinkDark
//////        )
//////        Spacer(modifier = Modifier.height(8.dp))
//////        Text(
//////            "Browse our services and add something special",
//////            style = MaterialTheme.typography.bodyMedium,
//////            color = Pink80,
//////            textAlign = TextAlign.Center
//////        )
//////        Spacer(modifier = Modifier.height(24.dp))
//////        Button(
//////            onClick = {
//////                navController.navigate("services") {
//////                    popUpTo("home") { saveState = true }
//////                    launchSingleTop = true
//////                    restoreState = true
//////                }
//////            },
//////            colors = ButtonDefaults.buttonColors(
//////                containerColor = HotPink,
//////                contentColor = Color.White
//////            ),
//////            shape = RoundedCornerShape(12.dp)
//////        ) {
//////            Text("Explore Services")
//////        }
//////    }
//////}
//////
//////@Composable
//////private fun CartItemCard(
//////    cartItem: CartItem,
//////    onQuantityChange: (Int) -> Unit,
//////    onPersonsChange: (Int) -> Unit,
//////    onRemove: () -> Unit
//////) {
//////    Card(
//////        modifier = Modifier
//////            .padding(horizontal = 16.dp, vertical = 8.dp)
//////            .fillMaxWidth(),
//////        shape = RoundedCornerShape(20.dp),
//////        colors = CardDefaults.cardColors(containerColor = Color.White),
//////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//////    ) {
//////        Column(modifier = Modifier.padding(16.dp)) {
//////            Row {
//////                Box(
//////                    modifier = Modifier
//////                        .size(100.dp)
//////                        .clip(RoundedCornerShape(12.dp))
//////                        .background(Pink10)
//////                ) {
//////                    if (cartItem.imageUrl.isNotEmpty()) {
//////                        AsyncImage(
//////                            model = cartItem.imageUrl,
//////                            contentDescription = cartItem.name,
//////                            contentScale = ContentScale.Crop,
//////                            modifier = Modifier.fillMaxSize()
//////                        )
//////                    } else {
//////                        Icon(
//////                            imageVector = Icons.Default.Spa,
//////                            contentDescription = "Service Image",
//////                            tint = HotPink,
//////                            modifier = Modifier
//////                                .size(60.dp)
//////                                .align(Alignment.Center)
//////                        )
//////                    }
//////                }
//////
//////                Spacer(modifier = Modifier.width(16.dp))
//////
//////                Column(modifier = Modifier.weight(1f)) {
//////                    Text(
//////                        cartItem.name,
//////                        style = MaterialTheme.typography.titleSmall,
//////                        fontWeight = FontWeight.Bold,
//////                        color = HotPinkDark
//////                    )
//////
//////                    Spacer(modifier = Modifier.height(4.dp))
//////
//////                    // Display duration
//////                    cartItem.duration?.let {
//////                        Text(
//////                            it,
//////                            style = MaterialTheme.typography.labelSmall,
//////                            color = Pink80
//////                        )
//////                    }
//////
//////                    Spacer(modifier = Modifier.height(8.dp))
//////
//////                    Row(verticalAlignment = Alignment.CenterVertically) {
//////                        Text(
//////                            "₹${cartItem.price}",
//////                            style = MaterialTheme.typography.bodyMedium,
//////                            fontWeight = FontWeight.Bold,
//////                            color = HotPink
//////                        )
//////
//////                        if (cartItem.originalPrice != null) {
//////                            Spacer(modifier = Modifier.width(8.dp))
//////                            Text(
//////                                "₹${cartItem.originalPrice}",
//////                                style = MaterialTheme.typography.labelSmall,
//////                                color = Color.Gray,
//////                                textDecoration = TextDecoration.LineThrough
//////                            )
//////                        }
//////                    }
//////
//////                    Spacer(modifier = Modifier.height(8.dp))
//////
//////                    // Total price for this item
//////                    val itemTotal = cartItem.price * cartItem.quantity * cartItem.persons
//////                    Text(
//////                        "Total: ₹${"%.2f".format(itemTotal)}",
//////                        style = MaterialTheme.typography.bodySmall,
//////                        fontWeight = FontWeight.Bold,
//////                        color = HotPinkDark
//////                    )
//////                }
//////            }
//////
//////            Spacer(modifier = Modifier.height(12.dp))
//////
//////            Row(
//////                verticalAlignment = Alignment.CenterVertically,
//////                horizontalArrangement = Arrangement.SpaceBetween,
//////                modifier = Modifier.fillMaxWidth()
//////            ) {
//////                // Quantity selector
//////                Column {
//////                    Text("Quantity:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
//////                    QuantitySelector(
//////                        quantity = cartItem.quantity,
//////                        onQuantityChange = onQuantityChange
//////                    )
//////                }
//////
//////                // Persons selector
//////                Column {
//////                    Text("Persons:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
//////                    PersonsSelector(
//////                        persons = cartItem.persons,
//////                        onPersonsChange = onPersonsChange
//////                    )
//////                }
//////
//////                // Remove button
//////                IconButton(
//////                    onClick = onRemove,
//////                    modifier = Modifier
//////                        .size(32.dp)
//////                        .clip(CircleShape)
//////                ) {
//////                    Icon(
//////                        imageVector = Icons.Default.Delete,
//////                        contentDescription = "Remove",
//////                        tint = HotPink
//////                    )
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////private fun QuantitySelector(
//////    quantity: Int,
//////    onQuantityChange: (Int) -> Unit
//////) {
//////    Surface(
//////        shape = RoundedCornerShape(12.dp),
//////        border = BorderStroke(1.dp, Pink20),
//////        modifier = Modifier.height(32.dp)
//////    ) {
//////        Row(
//////            verticalAlignment = Alignment.CenterVertically,
//////            horizontalArrangement = Arrangement.Center
//////        ) {
//////            IconButton(
//////                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
//////                modifier = Modifier.size(32.dp)
//////            ) {
//////                Icon(
//////                    imageVector = Icons.Default.Remove,
//////                    contentDescription = "Decrease",
//////                    tint = if (quantity > 1) HotPink else Color.LightGray
//////                )
//////            }
//////
//////            Text(
//////                text = quantity.toString(),
//////                style = MaterialTheme.typography.bodyMedium,
//////                fontWeight = FontWeight.Bold,
//////                color = HotPinkDark,
//////                modifier = Modifier.padding(horizontal = 8.dp)
//////            )
//////
//////            IconButton(
//////                onClick = { onQuantityChange(quantity + 1) },
//////                modifier = Modifier.size(32.dp)
//////            ) {
//////                Icon(
//////                    imageVector = Icons.Default.Add,
//////                    contentDescription = "Increase",
//////                    tint = HotPink
//////                )
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////private fun PersonsSelector(
//////    persons: Int,
//////    onPersonsChange: (Int) -> Unit
//////) {
//////    Surface(
//////        shape = RoundedCornerShape(12.dp),
//////        border = BorderStroke(1.dp, Pink20),
//////        modifier = Modifier.height(32.dp)
//////    ) {
//////        Row(
//////            verticalAlignment = Alignment.CenterVertically,
//////            horizontalArrangement = Arrangement.Center
//////        ) {
//////            IconButton(
//////                onClick = { if (persons > 1) onPersonsChange(persons - 1) },
//////                modifier = Modifier.size(32.dp)
//////            ) {
//////                Icon(
//////                    imageVector = Icons.Default.Remove,
//////                    contentDescription = "Decrease Persons",
//////                    tint = if (persons > 1) HotPink else Color.LightGray
//////                )
//////            }
//////
//////            Text(
//////                text = persons.toString(),
//////                style = MaterialTheme.typography.bodyMedium,
//////                fontWeight = FontWeight.Bold,
//////                color = HotPinkDark,
//////                modifier = Modifier.padding(horizontal = 8.dp)
//////            )
//////
//////            IconButton(
//////                onClick = { onPersonsChange(persons + 1) },
//////                modifier = Modifier.size(32.dp)
//////            ) {
//////                Icon(
//////                    imageVector = Icons.Default.Add,
//////                    contentDescription = "Increase Persons",
//////                    tint = HotPink
//////                )
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////private fun PriceRow(
//////    label: String,
//////    amount: Double,
//////    color: Color = Color.Gray,
//////    fontWeight: FontWeight = FontWeight.Normal
//////) {
//////    Row(
//////        modifier = Modifier.fillMaxWidth(),
//////        horizontalArrangement = Arrangement.SpaceBetween
//////    ) {
//////        Text(
//////            label,
//////            style = MaterialTheme.typography.bodyMedium,
//////            color = color,
//////            fontWeight = fontWeight
//////        )
//////        Text(
//////            "₹${"%.2f".format(amount)}",
//////            style = MaterialTheme.typography.bodyMedium,
//////            color = color,
//////            fontWeight = fontWeight
//////        )
//////    }
//////}
//////
//////private fun calculateSubtotal(items: List<CartItem>): Double {
//////    return items.sumOf { it.price * it.quantity * it.persons }
//////}
//////
//////private fun calculateDiscount(items: List<CartItem>, coupon: Coupon?): Double {
//////    if (coupon == null) return 0.0
//////    val subtotal = calculateSubtotal(items)
//////    return (subtotal * coupon.discountPercentage / 100).coerceAtMost(
//////        coupon.maxDiscount ?: Double.MAX_VALUE
//////    )
//////}
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
////
////
////
////
////
////
////package com.beauty.parler.activity
////
////import android.app.DatePickerDialog
////import android.app.TimePickerDialog
////import android.content.Context
////import android.util.Log
////import android.widget.Toast
////import androidx.compose.foundation.BorderStroke
////import androidx.compose.foundation.background
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.Arrangement
////import androidx.compose.foundation.layout.Box
////import androidx.compose.foundation.layout.Column
////import androidx.compose.foundation.layout.Row
////import androidx.compose.foundation.layout.Spacer
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.fillMaxWidth
////import androidx.compose.foundation.layout.height
////import androidx.compose.foundation.layout.padding
////import androidx.compose.foundation.layout.size
////import androidx.compose.foundation.layout.width
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.lazy.items
////import androidx.compose.foundation.rememberScrollState
////import androidx.compose.foundation.shape.CircleShape
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.foundation.text.KeyboardOptions
////import androidx.compose.foundation.verticalScroll
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.AccessTime
////import androidx.compose.material.icons.filled.Add
////import androidx.compose.material.icons.filled.ArrowBack
////import androidx.compose.material.icons.filled.CalendarToday
////import androidx.compose.material.icons.filled.CheckCircle
////import androidx.compose.material.icons.filled.Delete
////import androidx.compose.material.icons.filled.Remove
////import androidx.compose.material.icons.filled.ShoppingCart
////import androidx.compose.material.icons.filled.Spa
////import androidx.compose.material3.AlertDialog
////import androidx.compose.material3.Button
////import androidx.compose.material3.ButtonDefaults
////import androidx.compose.material3.Card
////import androidx.compose.material3.CardDefaults
////import androidx.compose.material3.CenterAlignedTopAppBar
////import androidx.compose.material3.CircularProgressIndicator
////import androidx.compose.material3.Divider
////import androidx.compose.material3.ExperimentalMaterial3Api
////import androidx.compose.material3.Icon
////import androidx.compose.material3.IconButton
////import androidx.compose.material3.MaterialTheme
////import androidx.compose.material3.OutlinedButton
////import androidx.compose.material3.OutlinedTextField
////import androidx.compose.material3.OutlinedTextFieldDefaults
////import androidx.compose.material3.Scaffold
////import androidx.compose.material3.Surface
////import androidx.compose.material3.Text
////import androidx.compose.material3.TextButton
////import androidx.compose.material3.TopAppBarDefaults
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.LaunchedEffect
////import androidx.compose.runtime.collectAsState
////import androidx.compose.runtime.getValue
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.remember
////import androidx.compose.runtime.setValue
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.draw.shadow
////import androidx.compose.ui.graphics.Brush
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.text.input.KeyboardType
////import androidx.compose.ui.text.style.TextAlign
////import androidx.compose.ui.text.style.TextDecoration
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.lifecycle.viewmodel.compose.viewModel
////import androidx.navigation.NavController
////import coil.compose.AsyncImage
////import com.beauty.parler.model.CartItem
////import com.beauty.parler.shared.CartRepository
////import com.beauty.parler.shared.CartViewModel
////import com.beauty.parler.shared.CartViewModelFactory
////import com.beauty.parler.ui.theme.HotPink
////import com.beauty.parler.ui.theme.HotPinkDark
////import com.beauty.parler.ui.theme.Pink10
////import com.beauty.parler.ui.theme.Pink20
////import com.beauty.parler.ui.theme.Pink40
////import com.beauty.parler.ui.theme.Pink80
////import com.google.firebase.Timestamp
////import com.google.firebase.firestore.FirebaseFirestore
////import kotlinx.coroutines.tasks.await
////import java.text.SimpleDateFormat
////import java.util.Calendar
////import java.util.Locale
////import java.util.UUID
////
////data class Coupon(
////    val id: String = "",
////    val code: String = "",
////    val discountPercentage: Int = 0,
////    val title: String = "",
////    val description: String = "",
////    val validUntil: Long = 0L,
////    val formattedDate: String = "",
////    val maxDiscount: Double? = null
////)
////
////data class Order(
////    val orderId: String = "",
////    val customerName: String = "",
////    val phoneNumber: String = "",
////    val address: String = "",
////    val appointmentDate: String = "",
////    val appointmentTime: String = "",
////    val items: List<CartItem> = emptyList(),
////    val subtotal: Double = 0.0,
////    val discount: Double = 0.0,
////    val total: Double = 0.0,
////    val coupon: Coupon? = null,
////    val status: String = "Pending",
////    val createdAt: Timestamp = Timestamp.now()
////)
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun CartScreen(navController: NavController, viewModel: CartViewModel) {
////    val context = LocalContext.current
////    val cartRepository = remember { CartRepository(context) }
////    val factory = remember { CartViewModelFactory(cartRepository) }
////    val cartViewModel: CartViewModel = viewModel(factory = factory)
////    val cartItems by cartViewModel.cartItems.collectAsState()
////    var showCouponDialog by remember { mutableStateOf(false) }
////    var couponCode by remember { mutableStateOf("") }
////    var selectedCoupon by remember { mutableStateOf<Coupon?>(null) }
////    var showBookingDialog by remember { mutableStateOf(false) }
////    var showConfirmationScreen by remember { mutableStateOf(false) }
////    var orderDetails by remember { mutableStateOf<Order?>(null) }
////    var availableCoupons by remember { mutableStateOf<List<Coupon>>(emptyList()) }
////    var isLoadingCoupons by remember { mutableStateOf(false) }
////    var couponError by remember { mutableStateOf<String?>(null) }
////    var isProcessingOrder by remember { mutableStateOf(false) }
////
////    val db = FirebaseFirestore.getInstance()
////
////    // Load coupons from Firestore
////    LaunchedEffect(Unit) {
////        isLoadingCoupons = true
////        try {
////            val result = db.collection("offers")
////                .whereGreaterThanOrEqualTo("validDate", System.currentTimeMillis())
////                .get()
////                .await()
////
////            availableCoupons = result.documents.mapNotNull { doc ->
////                try {
////                    Coupon(
////                        id = doc.id,
////                        code = doc.getString("useCode") ?: "",
////                        discountPercentage = doc.getLong("discountPercentage")?.toInt() ?: 0,
////                        title = doc.getString("title") ?: "",
////                        description = doc.getString("description") ?: "",
////                        validUntil = doc.getLong("validDate") ?: 0L,
////                        formattedDate = doc.getString("formattedDate") ?: "",
////                        maxDiscount = doc.getDouble("maxDiscount")
////                    )
////                } catch (e: Exception) {
////                    Log.e("CartScreen", "Error parsing coupon ${doc.id}", e)
////                    null
////                }
////            }
////        } catch (e: Exception) {
////            couponError = "Failed to load coupons. Please try again."
////            Log.e("CartScreen", "Error loading coupons", e)
////        } finally {
////            isLoadingCoupons = false
////        }
////    }
////
////    // Calculate prices
////    val subtotal = calculateSubtotal(cartItems)
////    val discount = calculateDiscount(cartItems, selectedCoupon)
////    val total = subtotal - discount
////
////    // Show confirmation screen if order is placed
////    if (showConfirmationScreen && orderDetails != null) {
////        OrderConfirmationScreen(
////            order = orderDetails!!,
////            onBackToHome = {
////                showConfirmationScreen = false
////                orderDetails = null
////                navController.popBackStack()
////            }
////        )
////        return
////    }
////
////    Scaffold(
////        topBar = {
////            CenterAlignedTopAppBar(
////                title = {
////                    Text(
////                        "Your Cart",
////                        style = MaterialTheme.typography.titleLarge,
////                        fontWeight = FontWeight.Bold,
////                        color = HotPinkDark
////                    )
////                },
////                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
////                    containerColor = Color.White
////                ),
////                navigationIcon = {
////                    IconButton(onClick = { navController.popBackStack() }) {
////                        Icon(
////                            imageVector = Icons.Default.ArrowBack,
////                            contentDescription = "Back",
////                            tint = HotPink
////                        )
////                    }
////                }
////            )
////        },
////        bottomBar = {
////            Surface(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .shadow(elevation = 16.dp),
////                color = Color.White
////            ) {
////                Column(
////                    modifier = Modifier.padding(16.dp)
////                ) {
////                    PriceRow(
////                        label = "Subtotal (${cartItems.size} items):",
////                        amount = subtotal
////                    )
////
////                    if (selectedCoupon != null) {
////                        PriceRow(
////                            label = "Discount (${selectedCoupon?.code}):",
////                            amount = -discount,
////                            color = HotPink
////                        )
////                    }
////
////                    Divider(modifier = Modifier.padding(vertical = 8.dp))
////
////                    PriceRow(
////                        label = "Total Amount:",
////                        amount = total,
////                        color = HotPinkDark,
////                        fontWeight = FontWeight.Bold
////                    )
////
////                    Spacer(modifier = Modifier.height(16.dp))
////
////                    Button(
////                        onClick = { showBookingDialog = true },
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .height(50.dp),
////                        colors = ButtonDefaults.buttonColors(
////                            containerColor = HotPink,
////                            contentColor = Color.White
////                        ),
////                        shape = RoundedCornerShape(12.dp),
////                        enabled = cartItems.isNotEmpty() && !isProcessingOrder,
////                        elevation = ButtonDefaults.buttonElevation(
////                            defaultElevation = 8.dp,
////                            pressedElevation = 4.dp
////                        )
////                    ) {
////                        if (isProcessingOrder) {
////                            CircularProgressIndicator(
////                                modifier = Modifier.size(20.dp),
////                                color = Color.White,
////                                strokeWidth = 2.dp
////                            )
////                        } else {
////                            Text(
////                                "Book Appointment",
////                                fontWeight = FontWeight.Bold,
////                                fontSize = 16.sp
////                            )
////                        }
////                    }
////                }
////            }
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
////            LazyColumn(
////                modifier = Modifier
////                    .padding(innerPadding)
////                    .fillMaxSize()
////            ) {
////                if (cartItems.isEmpty()) {
////                    item {
////                        EmptyCartView(navController)
////                    }
////                } else {
////                    item {
////                        Text(
////                            text = "Scheduled Services",
////                            style = MaterialTheme.typography.titleMedium,
////                            fontWeight = FontWeight.Bold,
////                            color = HotPinkDark,
////                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
////                        )
////                    }
////
////                    items(cartItems) { cartItem ->
////                        CartItemCard(
////                            cartItem = cartItem,
////                            onQuantityChange = { newQuantity ->
////                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
////                            },
////                            onPersonsChange = { newPersons ->
////                                cartItem.id?.let { cartViewModel.updatePersons(it, newPersons) }
////                            },
////                            onRemove = {
////                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
////                            }
////                        )
////                    }
////                }
////            }
////        }
////    }
////
////    // Booking Confirmation Dialog
////    if (showBookingDialog) {
////        BookingConfirmationDialog(
////            cartItems = cartItems,
////            subtotal = subtotal,
////            discount = discount,
////            total = total,
////            onDismiss = { showBookingDialog = false },
////            onConfirm = { customerName, phoneNumber, address, date, time ->
////                isProcessingOrder = true
////                showBookingDialog = false
////
////                // Store order in Firestore
////                storeOrderInFirestore(
////                    context = context,
////                    cartItems = cartItems,
////                    subtotal = subtotal,
////                    discount = discount,
////                    total = total,
////                    coupon = selectedCoupon,
////                    customerName = customerName,
////                    phoneNumber = phoneNumber,
////                    address = address,
////                    date = date,
////                    time = time,
////                    onSuccess = { order ->
////                        // Clear cart
////                        cartViewModel.clearCart()
////
////                        // Show confirmation screen
////                        orderDetails = order
////                        showConfirmationScreen = true
////                        isProcessingOrder = false
////                    },
////                    onError = { error ->
////                        Toast.makeText(context, "Failed to book appointment: $error", Toast.LENGTH_LONG).show()
////                        isProcessingOrder = false
////                    }
////                )
////            }
////        )
////    }
////}
////
////private fun storeOrderInFirestore(
////    context: Context,
////    cartItems: List<CartItem>,
////    subtotal: Double,
////    discount: Double,
////    total: Double,
////    coupon: Coupon?,
////    customerName: String,
////    phoneNumber: String,
////    address: String,
////    date: String,
////    time: String,
////    onSuccess: (Order) -> Unit,
////    onError: (String) -> Unit
////) {
////    val db = FirebaseFirestore.getInstance()
////    val orderId = UUID.randomUUID().toString()
////
////    val order = Order(
////        orderId = orderId,
////        customerName = customerName,
////        phoneNumber = phoneNumber,
////        address = address,
////        appointmentDate = date,
////        appointmentTime = time,
////        items = cartItems,
////        subtotal = subtotal,
////        discount = discount,
////        total = total,
////        coupon = coupon,
////        status = "Pending",
////        createdAt = Timestamp.now()
////    )
////
////    db.collection("orders")
////        .document(orderId)
////        .set(order)
////        .addOnSuccessListener {
////            onSuccess(order)
////        }
////        .addOnFailureListener { e ->
////            onError(e.message ?: "Unknown error")
////        }
////}
////
////@Composable
////fun OrderConfirmationScreen(order: Order, onBackToHome: () -> Unit) {
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(
////                brush = Brush.verticalGradient(
////                    colors = listOf(Pink10, Color.White),
////                    startY = 0f,
////                    endY = 500f
////                )
////            )
////            .padding(24.dp),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
////    ) {
////        Icon(
////            imageVector = Icons.Default.CheckCircle,
////            contentDescription = "Success",
////            tint = HotPink,
////            modifier = Modifier.size(80.dp)
////        )
////
////        Spacer(modifier = Modifier.height(24.dp))
////
////        Text(
////            "Appointment Booked!",
////            style = MaterialTheme.typography.headlineMedium,
////            fontWeight = FontWeight.Bold,
////            color = HotPinkDark,
////            textAlign = TextAlign.Center
////        )
////
//////        Spacer(modifier = Modifier.height(16.dp))
////
//////        Text(
//////            "Your appointment has been successfully booked",
//////            style = MaterialTheme.typography.bodyLarge,
//////            color = Pink80,
//////            textAlign = TextAlign.Center
//////        )
////
//////        Spacer(modifier = Modifier.height(8.dp))
////
//////        Text(
//////            "Order ID: ${order.orderId}",
//////            style = MaterialTheme.typography.bodyMedium,
//////            color = Color.Gray,
//////            textAlign = TextAlign.Center
//////        )
////
//////        Spacer(modifier = Modifier.height(32.dp))
////
////        Card(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(horizontal = 16.dp),
////            shape = RoundedCornerShape(16.dp),
////            colors = CardDefaults.cardColors(containerColor = Color.White)
////        ) {
////            Column(modifier = Modifier.padding(16.dp)) {
////                Text(
////                    "Appointment Details",
////                    style = MaterialTheme.typography.titleMedium,
////                    fontWeight = FontWeight.Bold,
////                    color = HotPinkDark
////                )
////
////                Spacer(modifier = Modifier.height(12.dp))
////
////                DetailRow("Date", order.appointmentDate)
////                DetailRow("Time", order.appointmentTime)
////                DetailRow("Customer", order.customerName)
////                DetailRow("Phone", order.phoneNumber)
////
////                Spacer(modifier = Modifier.height(12.dp))
////
////                Text(
////                    "Services (${order.items.size})",
////                    style = MaterialTheme.typography.titleSmall,
////                    fontWeight = FontWeight.Bold,
////                    color = HotPinkDark
////                )
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                order.items.forEach { item ->
////                    Text(
////                        "• ${item.name} - ₹${item.price}",
////                        style = MaterialTheme.typography.bodySmall,
////                        color = Color.Gray
////                    )
////                }
////
////                Spacer(modifier = Modifier.height(12.dp))
////
////                Divider()
////
////                Spacer(modifier = Modifier.height(12.dp))
////
////                DetailRow("Subtotal", "₹${"%.2f".format(order.subtotal)}")
////
////                if (order.discount > 0) {
////                    DetailRow("Discount", "-₹${"%.2f".format(order.discount)}")
////                }
////
////                DetailRow("Total", "₹${"%.2f".format(order.total)}", HotPinkDark, FontWeight.Bold)
////            }
////        }
////
////        Spacer(modifier = Modifier.height(32.dp))
////
////        Button(
////            onClick = onBackToHome,
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(50.dp),
////            colors = ButtonDefaults.buttonColors(
////                containerColor = HotPink,
////                contentColor = Color.White
////            ),
////            shape = RoundedCornerShape(12.dp)
////        ) {
////            Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 16.sp)
////        }
////    }
////}
////
////@Composable
////fun DetailRow(
////    label: String,
////    value: String,
////    color: Color = Color.Gray,
////    fontWeight: FontWeight = FontWeight.Normal
////) {
////    Row(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(vertical = 4.dp),
////        horizontalArrangement = Arrangement.SpaceBetween
////    ) {
////        Text(
////            label,
////            style = MaterialTheme.typography.bodyMedium,
////            color = Color.Gray
////        )
////        Text(
////            value,
////            style = MaterialTheme.typography.bodyMedium,
////            color = color,
////            fontWeight = fontWeight
////        )
////    }
////}
////
////@Composable
////private fun BookingConfirmationDialog(
////    cartItems: List<CartItem>,
////    subtotal: Double,
////    discount: Double,
////    total: Double,
////    onDismiss: () -> Unit,
////    onConfirm: (String, String, String, String, String) -> Unit
////) {
////    var customerName by remember { mutableStateOf("") }
////    var phoneNumber by remember { mutableStateOf("") }
////    var address by remember { mutableStateOf("") }
////    var date by remember { mutableStateOf("") }
////    var time by remember { mutableStateOf("") }
////    val context = LocalContext.current
////
////    AlertDialog(
////        onDismissRequest = onDismiss,
////        title = {
////            Text(
////                "Book Appointment",
////                style = MaterialTheme.typography.titleLarge,
////                color = HotPinkDark
////            )
////        },
////        text = {
////            Column(
////                modifier = Modifier.verticalScroll(rememberScrollState())
////            ) {
////                // Customer Information Inputs
////                OutlinedTextField(
////                    value = customerName,
////                    onValueChange = { customerName = it },
////                    label = { Text("Customer Name") },
////                    modifier = Modifier.fillMaxWidth(),
////                    colors = OutlinedTextFieldDefaults.colors(
////                        focusedBorderColor = HotPink,
////                        focusedLabelColor = HotPink
////                    )
////                )
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                OutlinedTextField(
////                    value = phoneNumber,
////                    onValueChange = { phoneNumber = it },
////                    label = { Text("Phone Number") },
////                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
////                    modifier = Modifier.fillMaxWidth(),
////                    colors = OutlinedTextFieldDefaults.colors(
////                        focusedBorderColor = HotPink,
////                        focusedLabelColor = HotPink
////                    )
////                )
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                OutlinedTextField(
////                    value = address,
////                    onValueChange = { address = it },
////                    label = { Text("Address") },
////                    modifier = Modifier.fillMaxWidth(),
////                    colors = OutlinedTextFieldDefaults.colors(
////                        focusedBorderColor = HotPink,
////                        focusedLabelColor = HotPink
////                    )
////                )
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                Row(
////                    horizontalArrangement = Arrangement.spacedBy(8.dp)
////                ) {
////                    OutlinedTextField(
////                        value = date,
////                        onValueChange = { date = it },
////                        label = { Text("Date") },
////                        modifier = Modifier.weight(1f),
////                        colors = OutlinedTextFieldDefaults.colors(
////                            focusedBorderColor = HotPink,
////                            focusedLabelColor = HotPink
////                        ),
////                        readOnly = true,
////                        trailingIcon = {
////                            IconButton(onClick = {
////                                showDatePicker(context) { selectedDate ->
////                                    date = selectedDate
////                                }
////                            }) {
////                                Icon(
////                                    Icons.Default.CalendarToday,
////                                    contentDescription = "Pick Date",
////                                    tint = HotPink
////                                )
////                            }
////                        }
////                    )
////
////                    OutlinedTextField(
////                        value = time,
////                        onValueChange = { time = it },
////                        label = { Text("Time") },
////                        modifier = Modifier.weight(1f),
////                        colors = OutlinedTextFieldDefaults.colors(
////                            focusedBorderColor = HotPink,
////                            focusedLabelColor = HotPink
////                        ),
////                        readOnly = true,
////                        trailingIcon = {
////                            IconButton(onClick = {
////                                showTimePicker(context) { selectedTime ->
////                                    time = selectedTime
////                                }
////                            }) {
////                                Icon(
////                                    Icons.Default.AccessTime,
////                                    contentDescription = "Pick Time",
////                                    tint = HotPink
////                                )
////                            }
////                        }
////                    )
////                }
////
////                Spacer(modifier = Modifier.height(16.dp))
////
////                // Services summary
////                Text(
////                    "Services Booked:",
////                    style = MaterialTheme.typography.bodyLarge,
////                    fontWeight = FontWeight.Bold,
////                    color = HotPinkDark
////                )
////
////                Spacer(modifier = Modifier.height(8.dp))
////
////                cartItems.forEach { item ->
////                    Text(
////                        "- ${item.name} (₹${item.price}) - ${item.duration} - ${item.persons} person(s)",
////                        color = Color.Gray
////                    )
////                }
////
////                Spacer(modifier = Modifier.height(16.dp))
////
////                PriceRow("Subtotal:", subtotal)
////
////                if (discount > 0) {
////                    PriceRow("Discount:", -discount, HotPink)
////                }
////
////                Divider(modifier = Modifier.padding(vertical = 8.dp))
////
////                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)
////
////                Spacer(modifier = Modifier.height(16.dp))
////
////                Text(
////                    "Your appointment details will be stored and confirmed",
////                    style = MaterialTheme.typography.bodySmall,
////                    color = Color.Gray
////                )
////            }
////        },
////        confirmButton = {
////            Button(
////                onClick = {
////                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
////                        address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
////                        onConfirm(customerName, phoneNumber, address, date, time)
////                    } else {
////                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
////                    }
////                },
////                colors = ButtonDefaults.buttonColors(
////                    containerColor = HotPink,
////                    contentColor = Color.White
////                ),
////                shape = RoundedCornerShape(12.dp)
////            ) {
////                Text("Confirm Appointment")
////            }
////        },
////        dismissButton = {
////            TextButton(
////                onClick = onDismiss,
////                shape = RoundedCornerShape(12.dp)
////            ) {
////                Text("Cancel", color = HotPink)
////            }
////        },
////        containerColor = Color.White,
////        shape = RoundedCornerShape(24.dp)
////    )
////}
////
////private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
////    val calendar = Calendar.getInstance()
////    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
////        calendar.set(Calendar.YEAR, year)
////        calendar.set(Calendar.MONTH, month)
////        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
////
////        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
////        onDateSelected(sdf.format(calendar.time))
////    }
////
////    DatePickerDialog(
////        context,
////        dateSetListener,
////        calendar.get(Calendar.YEAR),
////        calendar.get(Calendar.MONTH),
////        calendar.get(Calendar.DAY_OF_MONTH)
////    ).apply {
////        datePicker.minDate = System.currentTimeMillis() - 1000
////        show()
////    }
////}
////
////private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
////    val calendar = Calendar.getInstance()
////    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
////        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
////        calendar.set(Calendar.MINUTE, minute)
////
////        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
////        onTimeSelected(sdf.format(calendar.time))
////    }
////
////    TimePickerDialog(
////        context,
////        timeSetListener,
////        calendar.get(Calendar.HOUR_OF_DAY),
////        calendar.get(Calendar.MINUTE),
////        false
////    ).show()
////}
////
////@Composable
////private fun EmptyCartView(navController: NavController) {
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .padding(32.dp),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
////    ) {
////        Icon(
////            imageVector = Icons.Default.ShoppingCart,
////            contentDescription = "Empty Cart",
////            tint = Pink40,
////            modifier = Modifier.size(64.dp)
////        )
////        Spacer(modifier = Modifier.height(16.dp))
////        Text(
////            "Your cart is empty",
////            style = MaterialTheme.typography.titleLarge,
////            color = HotPinkDark
////        )
////        Spacer(modifier = Modifier.height(8.dp))
////        Text(
////            "Browse our services and add something special",
////            style = MaterialTheme.typography.bodyMedium,
////            color = Pink80,
////            textAlign = TextAlign.Center
////        )
////        Spacer(modifier = Modifier.height(24.dp))
////        Button(
////            onClick = {
////                navController.navigate("services") {
////                    popUpTo("home") { saveState = true }
////                    launchSingleTop = true
////                    restoreState = true
////                }
////            },
////            colors = ButtonDefaults.buttonColors(
////                containerColor = HotPink,
////                contentColor = Color.White
////            ),
////            shape = RoundedCornerShape(12.dp)
////        ) {
////            Text("Explore Services")
////        }
////    }
////}
////
////@Composable
////private fun CartItemCard(
////    cartItem: CartItem,
////    onQuantityChange: (Int) -> Unit,
////    onPersonsChange: (Int) -> Unit,
////    onRemove: () -> Unit
////) {
////    Card(
////        modifier = Modifier
////            .padding(horizontal = 16.dp, vertical = 8.dp)
////            .fillMaxWidth(),
////        shape = RoundedCornerShape(20.dp),
////        colors = CardDefaults.cardColors(containerColor = Color.White),
////        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
////    ) {
////        Column(modifier = Modifier.padding(16.dp)) {
////            Row {
////                Box(
////                    modifier = Modifier
////                        .size(100.dp)
////                        .clip(RoundedCornerShape(12.dp))
////                        .background(Pink10)
////                ) {
////                    if (cartItem.imageUrl.isNotEmpty()) {
////                        AsyncImage(
////                            model = cartItem.imageUrl,
////                            contentDescription = cartItem.name,
////                            contentScale = ContentScale.Crop,
////                            modifier = Modifier.fillMaxSize()
////                        )
////                    } else {
////                        Icon(
////                            imageVector = Icons.Default.Spa,
////                            contentDescription = "Service Image",
////                            tint = HotPink,
////                            modifier = Modifier
////                                .size(60.dp)
////                                .align(Alignment.Center)
////                        )
////                    }
////                }
////
////                Spacer(modifier = Modifier.width(16.dp))
////
////                Column(modifier = Modifier.weight(1f)) {
////                    Text(
////                        cartItem.name,
////                        style = MaterialTheme.typography.titleSmall,
////                        fontWeight = FontWeight.Bold,
////                        color = HotPinkDark
////                    )
////
////                    Spacer(modifier = Modifier.height(4.dp))
////
////                    // Display duration
////                    cartItem.duration?.let {
////                        Text(
////                            it,
////                            style = MaterialTheme.typography.labelSmall,
////                            color = Pink80
////                        )
////                    }
////
////                    Spacer(modifier = Modifier.height(8.dp))
////
////                    Row(verticalAlignment = Alignment.CenterVertically) {
////                        Text(
////                            "₹${cartItem.price}",
////                            style = MaterialTheme.typography.bodyMedium,
////                            fontWeight = FontWeight.Bold,
////                            color = HotPink
////                        )
////
////                        if (cartItem.originalPrice != null) {
////                            Spacer(modifier = Modifier.width(8.dp))
////                            Text(
////                                "₹${cartItem.originalPrice}",
////                                style = MaterialTheme.typography.labelSmall,
////                                color = Color.Gray,
////                                textDecoration = TextDecoration.LineThrough
////                            )
////                        }
////                    }
////
////                    Spacer(modifier = Modifier.height(8.dp))
////
////                    // Total price for this item
////                    val itemTotal = cartItem.price * cartItem.quantity * cartItem.persons
////                    Text(
////                        "Total: ₹${"%.2f".format(itemTotal)}",
////                        style = MaterialTheme.typography.bodySmall,
////                        fontWeight = FontWeight.Bold,
////                        color = HotPinkDark
////                    )
////                }
////            }
////
////            Spacer(modifier = Modifier.height(12.dp))
////
////            Row(
////                verticalAlignment = Alignment.CenterVertically,
////                horizontalArrangement = Arrangement.SpaceBetween,
////                modifier = Modifier.fillMaxWidth()
////            ) {
////                // Quantity selector
////                Column {
////                    Text("Quantity:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
////                    QuantitySelector(
////                        quantity = cartItem.quantity,
////                        onQuantityChange = onQuantityChange
////                    )
////                }
////
////                // Persons selector
////                Column {
////                    Text("Persons:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
////                    PersonsSelector(
////                        persons = cartItem.persons,
////                        onPersonsChange = onPersonsChange
////                    )
////                }
////
////                // Remove button
////                IconButton(
////                    onClick = onRemove,
////                    modifier = Modifier
////                        .size(32.dp)
////                        .clip(CircleShape)
////                ) {
////                    Icon(
////                        imageVector = Icons.Default.Delete,
////                        contentDescription = "Remove",
////                        tint = HotPink
////                    )
////                }
////            }
////        }
////    }
////}
////
////@Composable
////private fun QuantitySelector(
////    quantity: Int,
////    onQuantityChange: (Int) -> Unit
////) {
////    Surface(
////        shape = RoundedCornerShape(12.dp),
////        border = BorderStroke(1.dp, Pink20),
////        modifier = Modifier.height(32.dp)
////    ) {
////        Row(
////            verticalAlignment = Alignment.CenterVertically,
////            horizontalArrangement = Arrangement.Center
////        ) {
////            IconButton(
////                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
////                modifier = Modifier.size(32.dp)
////            ) {
////                Icon(
////                    imageVector = Icons.Default.Remove,
////                    contentDescription = "Decrease",
////                    tint = if (quantity > 1) HotPink else Color.LightGray
////                )
////            }
////
////            Text(
////                text = quantity.toString(),
////                style = MaterialTheme.typography.bodyMedium,
////                fontWeight = FontWeight.Bold,
////                color = HotPinkDark,
////                modifier = Modifier.padding(horizontal = 8.dp)
////            )
////
////            IconButton(
////                onClick = { onQuantityChange(quantity + 1) },
////                modifier = Modifier.size(32.dp)
////            ) {
////                Icon(
////                    imageVector = Icons.Default.Add,
////                    contentDescription = "Increase",
////                    tint = HotPink
////                )
////            }
////        }
////    }
////}
////
////@Composable
////private fun PersonsSelector(
////    persons: Int,
////    onPersonsChange: (Int) -> Unit
////) {
////    Surface(
////        shape = RoundedCornerShape(12.dp),
////        border = BorderStroke(1.dp, Pink20),
////        modifier = Modifier.height(32.dp)
////    ) {
////        Row(
////            verticalAlignment = Alignment.CenterVertically,
////            horizontalArrangement = Arrangement.Center
////        ) {
////            IconButton(
////                onClick = { if (persons > 1) onPersonsChange(persons - 1) },
////                modifier = Modifier.size(32.dp)
////            ) {
////                Icon(
////                    imageVector = Icons.Default.Remove,
////                    contentDescription = "Decrease Persons",
////                    tint = if (persons > 1) HotPink else Color.LightGray
////                )
////            }
////
////            Text(
////                text = persons.toString(),
////                style = MaterialTheme.typography.bodyMedium,
////                fontWeight = FontWeight.Bold,
////                color = HotPinkDark,
////                modifier = Modifier.padding(horizontal = 8.dp)
////            )
////
////            IconButton(
////                onClick = { onPersonsChange(persons + 1) },
////                modifier = Modifier.size(32.dp)
////            ) {
////                Icon(
////                    imageVector = Icons.Default.Add,
////                    contentDescription = "Increase Persons",
////                    tint = HotPink
////                )
////            }
////        }
////    }
////}
////
////@Composable
////private fun PriceRow(
////    label: String,
////    amount: Double,
////    color: Color = Color.Gray,
////    fontWeight: FontWeight = FontWeight.Normal
////) {
////    Row(
////        modifier = Modifier.fillMaxWidth(),
////        horizontalArrangement = Arrangement.SpaceBetween
////    ) {
////        Text(
////            label,
////            style = MaterialTheme.typography.bodyMedium,
////            color = color,
////            fontWeight = fontWeight
////        )
////        Text(
////            "₹${"%.2f".format(amount)}",
////            style = MaterialTheme.typography.bodyMedium,
////            color = color,
////            fontWeight = fontWeight
////        )
////    }
////}
////
////private fun calculateSubtotal(items: List<CartItem>): Double {
////    return items.sumOf { it.price * it.quantity * it.persons }
////}
////
////private fun calculateDiscount(items: List<CartItem>, coupon: Coupon?): Double {
////    if (coupon == null) return 0.0
////    val subtotal = calculateSubtotal(items)
////    return (subtotal * coupon.discountPercentage / 100).coerceAtMost(
////        coupon.maxDiscount ?: Double.MAX_VALUE
////    )
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
//
//
//
//
//package com.beauty.parler.activity
//
//import android.app.DatePickerDialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
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
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccessTime
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.CheckCircle
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Remove
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material.icons.filled.Spa
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.beauty.parler.model.CartItem
//import com.beauty.parler.shared.CartRepository
//import com.beauty.parler.shared.CartViewModel
//import com.beauty.parler.shared.CartViewModelFactory
//import com.beauty.parler.ui.theme.HotPink
//import com.beauty.parler.ui.theme.HotPinkDark
//import com.beauty.parler.ui.theme.Pink10
//import com.beauty.parler.ui.theme.Pink20
//import com.beauty.parler.ui.theme.Pink40
//import com.beauty.parler.ui.theme.Pink80
//import com.google.firebase.Timestamp
//import com.google.firebase.FirebaseException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.auth.PhoneAuthOptions
//import com.google.firebase.auth.PhoneAuthProvider
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Locale
//import java.util.UUID
//import java.util.concurrent.TimeUnit
//
//data class Coupon(
//    val id: String = "",
//    val code: String = "",
//    val discountPercentage: Int = 0,
//    val title: String = "",
//    val description: String = "",
//    val validUntil: Long = 0L,
//    val formattedDate: String = "",
//    val maxDiscount: Double? = null
//)
//
//data class Order(
//    val orderId: String = "",
//    val customerName: String = "",
//    val phoneNumber: String = "",
//    val address: String = "",
//    val appointmentDate: String = "",
//    val appointmentTime: String = "",
//    val items: List<CartItem> = emptyList(),
//    val subtotal: Double = 0.0,
//    val discount: Double = 0.0,
//    val total: Double = 0.0,
//    val coupon: Coupon? = null,
//    val status: String = "Pending",
//    val createdAt: Timestamp = Timestamp.now()
//)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CartScreen(navController: NavController, viewModel: CartViewModel) {
//    val context = LocalContext.current
//    val cartRepository = remember { CartRepository(context) }
//    val factory = remember { CartViewModelFactory(cartRepository) }
//    val cartViewModel: CartViewModel = viewModel(factory = factory)
//    val cartItems by cartViewModel.cartItems.collectAsState()
//    var showBookingDialog by remember { mutableStateOf(false) }
//    var showConfirmationScreen by remember { mutableStateOf(false) }
//    var orderDetails by remember { mutableStateOf<Order?>(null) }
//    var isProcessingOrder by remember { mutableStateOf(false) }
//
//    // Calculate prices
//    val subtotal = calculateSubtotal(cartItems)
//    val total = subtotal
//
//    // Show confirmation screen if order is placed
//    if (showConfirmationScreen && orderDetails != null) {
//        OrderConfirmationScreen(
//            order = orderDetails!!,
//            onBackToHome = {
//                showConfirmationScreen = false
//                orderDetails = null
//                navController.popBackStack()
//            }
//        )
//        return
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        "Your Cart",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.White
//                ),
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back",
//                            tint = HotPink
//                        )
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            Surface(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .shadow(elevation = 16.dp),
//                color = Color.White
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    PriceRow(
//                        label = "Subtotal (${cartItems.size} items):",
//                        amount = subtotal
//                    )
//
//                    Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//                    PriceRow(
//                        label = "Total Amount:",
//                        amount = total,
//                        color = HotPinkDark,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = { showBookingDialog = true },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(12.dp),
//                        enabled = cartItems.isNotEmpty() && !isProcessingOrder,
//                        elevation = ButtonDefaults.buttonElevation(
//                            defaultElevation = 8.dp,
//                            pressedElevation = 4.dp
//                        )
//                    ) {
//                        if (isProcessingOrder) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(20.dp),
//                                color = Color.White,
//                                strokeWidth = 2.dp
//                            )
//                        } else {
//                            Text(
//                                "Book Appointment",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 16.sp
//                            )
//                        }
//                    }
//                }
//            }
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
//            LazyColumn(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//            ) {
//                if (cartItems.isEmpty()) {
//                    item {
//                        EmptyCartView(navController)
//                    }
//                } else {
//                    item {
//                        Text(
//                            text = "Scheduled Services",
//                            style = MaterialTheme.typography.titleMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPinkDark,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                        )
//                    }
//
//                    items(cartItems) { cartItem ->
//                        CartItemCard(
//                            cartItem = cartItem,
//                            onQuantityChange = { newQuantity ->
//                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
//                            },
//                            onRemove = {
//                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    // Booking Confirmation Dialog
//    if (showBookingDialog) {
//        PhoneVerificationDialog(
//            onDismiss = { showBookingDialog = false },
//            onVerificationSuccess = { verifiedPhoneNumber ->
//                showBookingDialog = false
//                // Show customer details dialog after verification
//                showCustomerDetailsDialog(
//                    cartItems = cartItems,
//                    subtotal = subtotal,
//                    total = total,
//                    verifiedPhoneNumber = verifiedPhoneNumber,
//                    onConfirm = { customerName, address, date, time ->
//                        isProcessingOrder = true
//
//                        // Store order in Firestore
//                        storeOrderInFirestore(
//                            context = context,
//                            cartItems = cartItems,
//                            subtotal = subtotal,
//                            discount = 0.0,
//                            total = total,
//                            coupon = null,
//                            customerName = customerName,
//                            phoneNumber = verifiedPhoneNumber,
//                            address = address,
//                            date = date,
//                            time = time,
//                            onSuccess = { order ->
//                                // Clear cart
//                                cartViewModel.clearCart()
//
//                                // Show confirmation screen
//                                orderDetails = order
//                                showConfirmationScreen = true
//                                isProcessingOrder = false
//                            },
//                            onError = { error ->
//                                Toast.makeText(
//                                    context,
//                                    "Failed to book appointment: $error",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                isProcessingOrder = false
//                            }
//                        )
//                    }
//                )
//            }
//        )
//    }
//}
//
//@Composable
//private fun showCustomerDetailsDialog(
//    cartItems: List<CartItem>,
//    subtotal: Double,
//    total: Double,
//    verifiedPhoneNumber: String,
//    onConfirm: (String, String, String, String) -> Unit
//) {
//    var customerName by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var date by remember { mutableStateOf("") }
//    var time by remember { mutableStateOf("") }
//    val context = LocalContext.current
//
//    AlertDialog(
//        onDismissRequest = { /* Dialog can't be dismissed by clicking outside */ },
//        title = {
//            Text(
//                "Complete Your Booking",
//                style = MaterialTheme.typography.titleLarge,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column(
//                modifier = Modifier.verticalScroll(rememberScrollState())
//            ) {
//                // Customer Information Inputs
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
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                OutlinedTextField(
//                    value = verifiedPhoneNumber,
//                    onValueChange = { /* Read-only as it's already verified */ },
//                    label = { Text("Phone Number (Verified)") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = Color.Green,
//                        focusedLabelColor = Color.Green
//                    ),
//                    readOnly = true,
//                    trailingIcon = {
//                        Icon(
//                            Icons.Default.CheckCircle,
//                            contentDescription = "Verified",
//                            tint = Color.Green
//                        )
//                    }
//                )
//
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
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    OutlinedTextField(
//                        value = date,
//                        onValueChange = { date = it },
//                        label = { Text("Date") },
//                        modifier = Modifier.weight(1f),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink
//                        ),
//                        readOnly = true,
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                showDatePicker(context) { selectedDate ->
//                                    date = selectedDate
//                                }
//                            }) {
//                                Icon(
//                                    Icons.Default.CalendarToday,
//                                    contentDescription = "Pick Date",
//                                    tint = HotPink
//                                )
//                            }
//                        }
//                    )
//
//                    OutlinedTextField(
//                        value = time,
//                        onValueChange = { time = it },
//                        label = { Text("Time") },
//                        modifier = Modifier.weight(1f),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink
//                        ),
//                        readOnly = true,
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                showTimePicker(context) { selectedTime ->
//                                    time = selectedTime
//                                }
//                            }) {
//                                Icon(
//                                    Icons.Default.AccessTime,
//                                    contentDescription = "Pick Time",
//                                    tint = HotPink
//                                )
//                            }
//                        }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Services summary
//                Text(
//                    "Services Booked:",
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                cartItems.forEach { item ->
//                    Text(
//                        "- ${item.name} (₹${item.price}) - ${item.duration}",
//                        color = Color.Gray
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                PriceRow("Subtotal:", subtotal)
//
//                Divider(modifier = Modifier.padding(vertical = 8.dp))
//
//                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    "Your appointment details will be stored and confirmed",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//            }
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    if (customerName.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
//                        onConfirm(customerName, address, date, time)
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
//                Text("Confirm Appointment")
//            }
//        },
//        containerColor = Color.White,
//        shape = RoundedCornerShape(24.dp)
//    )
//}
//
//
//// Helper function for showing date picker
//private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
//    val calendar = Calendar.getInstance()
//    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//        calendar.set(Calendar.YEAR, year)
//        calendar.set(Calendar.MONTH, month)
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        onDateSelected(sdf.format(calendar.time))
//    }
//
//    DatePickerDialog(
//        context,
//        dateSetListener,
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    ).apply {
//        datePicker.minDate = System.currentTimeMillis() - 1000
//        show()
//    }
//}
//
//// Helper function for showing time picker
//private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
//    val calendar = Calendar.getInstance()
//    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//        calendar.set(Calendar.MINUTE, minute)
//
//        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        onTimeSelected(sdf.format(calendar.time))
//    }
//
//    TimePickerDialog(
//        context,
//        timeSetListener,
//        calendar.get(Calendar.HOUR_OF_DAY),
//        calendar.get(Calendar.MINUTE),
//        false
//    ).show()
//}
//
//// Price row composable
//@Composable
//private fun PriceRow(
//    label: String,
//    amount: Double,
//    color: Color = Color.Gray,
//    fontWeight: FontWeight = FontWeight.Normal
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            label,
//            style = MaterialTheme.typography.bodyMedium,
//            color = color,
//            fontWeight = fontWeight
//        )
//        Text(
//            "₹${"%.2f".format(amount)}",
//            style = MaterialTheme.typography.bodyMedium,
//            color = color,
//            fontWeight = fontWeight
//        )
//    }
//}
//
//@Composable
//private fun PhoneVerificationDialog(
//    onDismiss: () -> Unit,
//    onVerificationSuccess: (String) -> Unit
//) {
//    var phoneNumber by remember { mutableStateOf("") }
//    var verificationCode by remember { mutableStateOf("") }
//    var verificationId by remember { mutableStateOf<String?>(null) }
//    var isCodeSent by remember { mutableStateOf(false) }
//    var isLoading by remember { mutableStateOf(false) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    val context = LocalContext.current
//    val auth = FirebaseAuth.getInstance()
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = {
//            Text(
//                "Phone Verification",
//                style = MaterialTheme.typography.titleLarge,
//                color = HotPinkDark
//            )
//        },
//        text = {
//            Column {
//                if (!isCodeSent) {
//                    Text(
//                        "Enter your phone number to verify your appointment",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    OutlinedTextField(
//                        value = phoneNumber,
//                        onValueChange = {
//                            phoneNumber = it
//                            errorMessage = null
//                        },
//                        label = { Text("Phone Number") },
//                        placeholder = { Text("+91XXXXXXXXXX") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink
//                        )
//                    )
//
//                    errorMessage?.let {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            it,
//                            color = Color.Red,
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                } else {
//                    Text(
//                        "Enter the verification code sent to your phone",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    OutlinedTextField(
//                        value = verificationCode,
//                        onValueChange = {
//                            verificationCode = it
//                            errorMessage = null
//                        },
//                        label = { Text("Verification Code") },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = HotPink,
//                            focusedLabelColor = HotPink
//                        )
//                    )
//
//                    errorMessage?.let {
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            it,
//                            color = Color.Red,
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//            }
//        },
//        confirmButton = {
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(20.dp),
//                    color = HotPink,
//                    strokeWidth = 2.dp
//                )
//            } else {
//                if (!isCodeSent) {
//                    Button(
//                        onClick = {
//                            if (phoneNumber.isNotEmpty() && phoneNumber.length >= 10) {
//                                isLoading = true
//                                sendVerificationCode(phoneNumber, context, auth,
//                                    onSuccess = { id ->
//                                        verificationId = id
//                                        isCodeSent = true
//                                        isLoading = false
//                                        errorMessage = null
//                                    },
//                                    onError = { error ->
//                                        errorMessage = error
//                                        isLoading = false
//                                    }
//                                )
//                            } else {
//                                errorMessage = "Please enter a valid phone number"
//                            }
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text("Send Code")
//                    }
//                } else {
//                    Button(
//                        onClick = {
//                            if (verificationCode.isNotEmpty() && verificationId != null) {
//                                isLoading = true
//                                verifyCode(verificationCode, verificationId!!, auth,
//                                    onSuccess = {
//                                        isLoading = false
//                                        onVerificationSuccess(phoneNumber)
//                                    },
//                                    onError = { error ->
//                                        errorMessage = error
//                                        isLoading = false
//                                    }
//                                )
//                            } else {
//                                errorMessage = "Please enter the verification code"
//                            }
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = HotPink,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text("Verify & Continue")
//                    }
//                }
//            }
//        },
//        dismissButton = {
//            TextButton(
//                onClick = onDismiss,
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Text("Cancel", color = HotPink)
//            }
//        },
//        containerColor = Color.White,
//        shape = RoundedCornerShape(24.dp)
//    )
//}
//
//private fun sendVerificationCode(
//    phoneNumber: String,
//    context: Context,
//    auth: FirebaseAuth,
//    onSuccess: (String) -> Unit,
//    onError: (String) -> Unit
//) {
//    val options = PhoneAuthOptions.newBuilder(auth)
//        .setPhoneNumber("+91$phoneNumber") // India country code
//        .setTimeout(60L, TimeUnit.SECONDS)
//        .setActivity(context as android.app.Activity)
//        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // Auto verification, not needed for manual input
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                onError("Verification failed: ${e.message}")
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                onSuccess(verificationId)
//            }
//        })
//        .build()
//
//    PhoneAuthProvider.verifyPhoneNumber(options)
//}
//
//private fun verifyCode(
//    code: String,
//    verificationId: String,
//    auth: FirebaseAuth,
//    onSuccess: () -> Unit,
//    onError: (String) -> Unit
//) {
//    val credential = PhoneAuthProvider.getCredential(verificationId, code)
//    auth.signInWithCredential(credential)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful)
//                onSuccess()
//            else {
//                onError("Invalid verification code")
//            }
//        }
//}
//
//private fun storeOrderInFirestore(
//    context: Context,
//    cartItems: List<CartItem>,
//    subtotal: Double,
//    discount: Double,
//    total: Double,
//    coupon: Coupon?,
//    customerName: String,
//    phoneNumber: String,
//    address: String,
//    date: String,
//    time: String,
//    onSuccess: (Order) -> Unit,
//    onError: (String) -> Unit
//) {
//    val db = FirebaseFirestore.getInstance()
//    val orderId = UUID.randomUUID().toString()
//
//    val order = Order(
//        orderId = orderId,
//        customerName = customerName,
//        phoneNumber = phoneNumber,
//        address = address,
//        appointmentDate = date,
//        appointmentTime = time,
//        items = cartItems,
//        subtotal = subtotal,
//        discount = discount,
//        total = total,
//        coupon = coupon,
//        status = "Pending",
//        createdAt = Timestamp.now()
//    )
//
//    db.collection("orders")
//        .document(orderId)
//        .set(order)
//        .addOnSuccessListener {
//            onSuccess(order)
//        }
//        .addOnFailureListener { e ->
//            onError(e.message ?: "Unknown error")
//        }
//}
//
//@Composable
//fun OrderConfirmationScreen(order: Order, onBackToHome: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(Pink10, Color.White),
//                    startY = 0f,
//                    endY = 500f
//                )
//            )
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            imageVector = Icons.Default.CheckCircle,
//            contentDescription = "Success",
//            tint = HotPink,
//            modifier = Modifier.size(80.dp)
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text(
//            "Appointment Booked!",
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold,
//            color = HotPinkDark,
//            textAlign = TextAlign.Center
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    "Appointment Details",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                DetailRow("Date", order.appointmentDate)
//                DetailRow("Time", order.appointmentTime)
//                DetailRow("Customer", order.customerName)
//                DetailRow("Phone", order.phoneNumber)
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Text(
//                    "Services (${order.items.size})",
//                    style = MaterialTheme.typography.titleSmall,
//                    fontWeight = FontWeight.Bold,
//                    color = HotPinkDark
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                order.items.forEach { item ->
//                    Text(
//                        "• ${item.name} - ₹${item.price}",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Gray
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Divider()
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                DetailRow("Subtotal", "₹${"%.2f".format(order.subtotal)}")
//
//                if (order.discount > 0) {
//                    DetailRow("Discount", "-₹${"%.2f".format(order.discount)}")
//                }
//
//                DetailRow("Total", "₹${"%.2f".format(order.total)}", HotPinkDark, FontWeight.Bold)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Button(
//            onClick = onBackToHome,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = HotPink,
//                contentColor = Color.White
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//        }
//    }
//}
//
//@Composable
//fun DetailRow(
//    label: String,
//    value: String,
//    color: Color = Color.Gray,
//    fontWeight: FontWeight = FontWeight.Normal
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            label,
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//        Text(
//            value,
//            style = MaterialTheme.typography.bodyMedium,
//            color = color,
//            fontWeight = fontWeight
//        )
//    }
//}
//
//private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
//    val calendar = Calendar.getInstance()
//    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//        calendar.set(Calendar.YEAR, year)
//        calendar.set(Calendar.MONTH, month)
//        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        onDateSelected(sdf.format(calendar.time))
//    }
//
//    DatePickerDialog(
//        context,
//        dateSetListener,
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    ).apply {
//        datePicker.minDate = System.currentTimeMillis() - 1000
//        show()
//    }
//}
//
//private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
//    val calendar = Calendar.getInstance()
//    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//        calendar.set(Calendar.MINUTE, minute)
//
//        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
//        onTimeSelected(sdf.format(calendar.time))
//    }
//
//    TimePickerDialog(
//        context,
//        timeSetListener,
//        calendar.get(Calendar.HOUR_OF_DAY),
//        calendar.get(Calendar.MINUTE),
//        false
//    ).show()
//}
//
//@Composable
//private fun EmptyCartView(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Icon(
//            imageVector = Icons.Default.ShoppingCart,
//            contentDescription = "Empty Cart",
//            tint = Pink40,
//            modifier = Modifier.size(64.dp)
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(
//            "Your cart is empty",
//            style = MaterialTheme.typography.titleLarge,
//            color = HotPinkDark
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            "Browse our services and add something special",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Pink80,
//            textAlign = TextAlign.Center
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//        Button(
//            onClick = {
//                navController.navigate("services") {
//                    popUpTo("home") { saveState = true }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = HotPink,
//                contentColor = Color.White
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text("Explore Services")
//        }
//    }
//}
//
//@Composable
//private fun CartItemCard(
//    cartItem: CartItem,
//    onQuantityChange: (Int) -> Unit,
//    onRemove: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(20.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row {
//                Box(
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(Pink10)
//                ) {
//                    if (cartItem.imageUrl.isNotEmpty()) {
//                        AsyncImage(
//                            model = cartItem.imageUrl,
//                            contentDescription = cartItem.name,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    } else {
//                        Icon(
//                            imageVector = Icons.Default.Spa,
//                            contentDescription = "Service Image",
//                            tint = HotPink,
//                            modifier = Modifier
//                                .size(60.dp)
//                                .align(Alignment.Center)
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(
//                        cartItem.name,
//                        style = MaterialTheme.typography.titleSmall,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    // Display duration
//                    cartItem.duration?.let {
//                        Text(
//                            it,
//                            style = MaterialTheme.typography.labelSmall,
//                            color = Pink80
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Text(
//                            "₹${cartItem.price}",
//                            style = MaterialTheme.typography.bodyMedium,
//                            fontWeight = FontWeight.Bold,
//                            color = HotPink
//                        )
//
//                        if (cartItem.originalPrice != null) {
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                "₹${cartItem.originalPrice}",
//                                style = MaterialTheme.typography.labelSmall,
//                                color = Color.Gray,
//                                textDecoration = TextDecoration.LineThrough
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    // Total price for this item
//                    val itemTotal = cartItem.price * cartItem.quantity
//                    Text(
//                        "Total: ₹${"%.2f".format(itemTotal)}",
//                        style = MaterialTheme.typography.bodySmall,
//                        fontWeight = FontWeight.Bold,
//                        color = HotPinkDark
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                // Quantity selector
//                Column {
//                    Text("Quantity:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
//                    QuantitySelector(
//                        quantity = cartItem.quantity,
//                        onQuantityChange = onQuantityChange
//                    )
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                // Remove button
//                IconButton(
//                    onClick = onRemove,
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clip(CircleShape)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Remove",
//                        tint = HotPink
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun QuantitySelector(
//    quantity: Int,
//    onQuantityChange: (Int) -> Unit
//) {
//    Surface(
//        shape = RoundedCornerShape(12.dp),
//        border = BorderStroke(1.dp, Pink20),
//        modifier = Modifier.height(32.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            IconButton(
//                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
//                modifier = Modifier.size(32.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Remove,
//                    contentDescription = "Decrease",
//                    tint = if (quantity > 1) HotPink else Color.LightGray
//                )
//            }
//
//            Text(
//                text = quantity.toString(),
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.Bold,
//                color = HotPinkDark,
//                modifier = Modifier.padding(horizontal = 8.dp)
//            )
//
//            IconButton(
//                onClick = { onQuantityChange(quantity + 1) },
//                modifier = Modifier.size(32.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = "Increase",
//                    tint = HotPink
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun PriceRow(
//    label: String,
//    amount: Double,
//    color: Color = Color.Gray,
//    fontWeight: FontWeight = FontWeight.Normal
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            label,
//            style = MaterialTheme.typography.bodyMedium,
//            color = color,
//            fontWeight = fontWeight
//        )
//        Text(
//            "₹${"%.2f".format(amount)}",
//            style = MaterialTheme.typography.bodyMedium,
//            color = color,
//            fontWeight = fontWeight
//        )
//    }
//}
//
//private fun calculateSubtotal(items: List<CartItem>): Double {
//    return items.sumOf { it.price * it.quantity }
//}























package com.beauty.parler.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beauty.parler.model.CartItem
import com.beauty.parler.shared.CartRepository
import com.beauty.parler.shared.CartViewModel
import com.beauty.parler.shared.CartViewModelFactory
import com.beauty.parler.ui.theme.HotPink
import com.beauty.parler.ui.theme.HotPinkDark
import com.beauty.parler.ui.theme.Pink10
import com.beauty.parler.ui.theme.Pink20
import com.beauty.parler.ui.theme.Pink40
import com.beauty.parler.ui.theme.Pink80
import com.google.firebase.Timestamp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

data class Coupon(
    val id: String = "",
    val code: String = "",
    val discountPercentage: Int = 0,
    val title: String = "",
    val description: String = "",
    val validUntil: Long = 0L,
    val formattedDate: String = "",
    val maxDiscount: Double? = null
)

data class Order(
    val orderId: String = "",
    val customerName: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val appointmentDate: String = "",
    val appointmentTime: String = "",
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val discount: Double = 0.0,
    val total: Double = 0.0,
    val coupon: Coupon? = null,
    val status: String = "Pending",
    val createdAt: Timestamp = Timestamp.now()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel) {
    val context = LocalContext.current
    val cartRepository = remember { CartRepository(context) }
    val factory = remember { CartViewModelFactory(cartRepository) }
    val cartViewModel: CartViewModel = viewModel(factory = factory)
    val cartItems by cartViewModel.cartItems.collectAsState()
    var showBookingDialog by remember { mutableStateOf(false) }
    var showConfirmationScreen by remember { mutableStateOf(false) }
    var orderDetails by remember { mutableStateOf<Order?>(null) }
    var isProcessingOrder by remember { mutableStateOf(false) }

    // Calculate prices
    val subtotal = calculateSubtotal(cartItems)
    val total = subtotal

    // Show confirmation screen if order is placed
    if (showConfirmationScreen && orderDetails != null) {
        OrderConfirmationScreen(
            order = orderDetails!!,
            onBackToHome = {
                showConfirmationScreen = false
                orderDetails = null
                navController.popBackStack()
            }
        )
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Your Cart",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = HotPink
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    PriceRow(
                        label = "Subtotal (${cartItems.size} items):",
                        amount = subtotal
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    PriceRow(
                        label = "Total Amount:",
                        amount = total,
                        color = HotPinkDark,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showBookingDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = cartItems.isNotEmpty() && !isProcessingOrder,
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        if (isProcessingOrder) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Book Appointment",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
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

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (cartItems.isEmpty()) {
                    item {
                        EmptyCartView(navController)
                    }
                } else {
                    item {
                        Text(
                            text = "Scheduled Services",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = HotPinkDark,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    items(cartItems) { cartItem ->
                        CartItemCard(
                            cartItem = cartItem,
                            onQuantityChange = { newQuantity ->
                                cartItem.id?.let { cartViewModel.updateQuantity(it, newQuantity) }
                            },
                            onRemove = {
                                cartItem.id?.let { cartViewModel.removeFromCart(it) }
                            }
                        )
                    }
                }
            }
        }
    }

    // Booking Confirmation Dialog
    if (showBookingDialog) {
        CustomerDetailsDialog(
            cartItems = cartItems,
            subtotal = subtotal,
            total = total,
            onDismiss = { showBookingDialog = false },
            onConfirm = { customerName, phoneNumber, address, date, time ->
                isProcessingOrder = true
                showBookingDialog = false

                // Store order in Firestore
                storeOrderInFirestore(
                    context = context,
                    cartItems = cartItems,
                    subtotal = subtotal,
                    discount = 0.0,
                    total = total,
                    coupon = null,
                    customerName = customerName,
                    phoneNumber = phoneNumber,
                    address = address,
                    date = date,
                    time = time,
                    onSuccess = { order ->
                        // Clear cart
                        cartViewModel.clearCart()

                        // Show confirmation screen
                        orderDetails = order
                        showConfirmationScreen = true
                        isProcessingOrder = false
                    },
                    onError = { error ->
                        Toast.makeText(context, "Failed to book appointment: $error", Toast.LENGTH_LONG).show()
                        isProcessingOrder = false
                    }
                )
            }
        )
    }
}

@Composable
private fun CustomerDetailsDialog(
    cartItems: List<CartItem>,
    subtotal: Double,
    total: Double,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var isCodeSent by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Complete Your Booking",
                style = MaterialTheme.typography.titleLarge,
                color = HotPinkDark
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                // Customer Information Inputs
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = HotPink,
                        focusedLabelColor = HotPink
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                            errorMessage = null
                        },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HotPink,
                            focusedLabelColor = HotPink
                        ),
                        enabled = !isCodeSent
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (phoneNumber.isNotEmpty() && phoneNumber.length >= 10) {
                                isLoading = true
                                sendVerificationCode(phoneNumber, context, auth,
                                    onSuccess = { id ->
                                        verificationId = id
                                        isCodeSent = true
                                        isLoading = false
                                        errorMessage = null
                                    },
                                    onError = { error ->
                                        errorMessage = error
                                        isLoading = false
                                    }
                                )
                            } else {
                                errorMessage = "Please enter a valid phone number"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HotPink,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isCodeSent && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Send OTP")
                        }
                    }
                }

                if (isCodeSent) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = verificationCode,
                            onValueChange = {
                                verificationCode = it
                                errorMessage = null
                            },
                            label = { Text("Verification Code") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HotPink,
                                focusedLabelColor = HotPink
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (verificationCode.isNotEmpty() && verificationId != null) {
                                    isLoading = true
                                    verifyCode(verificationCode, verificationId!!, auth,
                                        onSuccess = {
                                            isLoading = false
                                            errorMessage = null
                                            // Mark phone as verified
                                            Toast.makeText(context, "Phone verified successfully", Toast.LENGTH_SHORT).show()
                                        },
                                        onError = { error ->
                                            errorMessage = error
                                            isLoading = false
                                        }
                                    )
                                } else {
                                    errorMessage = "Please enter the verification code"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HotPink,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Verify")
                            }
                        }
                    }
                }

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = HotPink,
                        focusedLabelColor = HotPink
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HotPink,
                            focusedLabelColor = HotPink
                        ),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context) { selectedDate ->
                                    date = selectedDate
                                }
                            }) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Pick Date",
                                    tint = HotPink
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HotPink,
                            focusedLabelColor = HotPink
                        ),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                showTimePicker(context) { selectedTime ->
                                    time = selectedTime
                                }
                            }) {
                                Icon(
                                    Icons.Default.AccessTime,
                                    contentDescription = "Pick Time",
                                    tint = HotPink
                                )
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Services summary
                Text(
                    "Services Booked:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                cartItems.forEach { item ->
                    Text(
                        "- ${item.name} (₹${item.price}) - ${item.duration}",
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PriceRow("Subtotal:", subtotal)

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                PriceRow("Total Amount:", total, HotPinkDark, FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Your appointment details will be stored and confirmed",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (customerName.isNotEmpty() && phoneNumber.isNotEmpty() &&
                        address.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() &&
                        verificationCode.isNotEmpty()) {
                        onConfirm(customerName, phoneNumber, address, date, time)
                    } else {
                        Toast.makeText(context, "Please fill all fields and verify phone", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = HotPink,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Confirm Appointment")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel", color = HotPink)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp)
    )
}

private fun sendVerificationCode(
    phoneNumber: String,
    context: Context,
    auth: FirebaseAuth,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber("+91$phoneNumber") // India country code
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(context as android.app.Activity)
        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Auto verification, not needed for manual input
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onError("Verification failed: ${e.message}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                onSuccess(verificationId)
            }
        })
        .build()

    PhoneAuthProvider.verifyPhoneNumber(options)
}

private fun verifyCode(
    code: String,
    verificationId: String,
    auth: FirebaseAuth,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val credential = PhoneAuthProvider.getCredential(verificationId, code)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError("Invalid verification code")
            }
        }
}

private fun storeOrderInFirestore(
    context: Context,
    cartItems: List<CartItem>,
    subtotal: Double,
    discount: Double,
    total: Double,
    coupon: Coupon?,
    customerName: String,
    phoneNumber: String,
    address: String,
    date: String,
    time: String,
    onSuccess: (Order) -> Unit,
    onError: (String) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val orderId = UUID.randomUUID().toString()

    val order = Order(
        orderId = orderId,
        customerName = customerName,
        phoneNumber = phoneNumber,
        address = address,
        appointmentDate = date,
        appointmentTime = time,
        items = cartItems,
        subtotal = subtotal,
        discount = discount,
        total = total,
        coupon = coupon,
        status = "Pending",
        createdAt = Timestamp.now()
    )

    db.collection("orders")
        .document(orderId)
        .set(order)
        .addOnSuccessListener {
            onSuccess(order)
        }
        .addOnFailureListener { e ->
            onError(e.message ?: "Unknown error")
        }
}

@Composable
fun OrderConfirmationScreen(order: Order, onBackToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Pink10, Color.White),
                    startY = 0f,
                    endY = 500f
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = HotPink,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Appointment Booked!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = HotPinkDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Appointment Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                DetailRow("Date", order.appointmentDate)
                DetailRow("Time", order.appointmentTime)
                DetailRow("Customer", order.customerName)
                DetailRow("Phone", order.phoneNumber)

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Services (${order.items.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = HotPinkDark
                )

                Spacer(modifier = Modifier.height(8.dp))

                order.items.forEach { item ->
                    Text(
                        "• ${item.name} - ₹${item.price}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                Spacer(modifier = Modifier.height(12.dp))

                DetailRow("Subtotal", "₹${"%.2f".format(order.subtotal)}")

                if (order.discount > 0) {
                    DetailRow("Discount", "-₹${"%.2f".format(order.discount)}")
                }

                DetailRow("Total", "₹${"%.2f".format(order.total)}", HotPinkDark, FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HotPink,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    color: Color = Color.Gray,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = fontWeight
        )
    }
}

private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        onDateSelected(sdf.format(calendar.time))
    }

    DatePickerDialog(
        context,
        dateSetListener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = System.currentTimeMillis() - 1000
        show()
    }
}

private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        onTimeSelected(sdf.format(calendar.time))
    }

    TimePickerDialog(
        context,
        timeSetListener,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}

@Composable
private fun EmptyCartView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Empty Cart",
            tint = Pink40,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Your cart is empty",
            style = MaterialTheme.typography.titleLarge,
            color = HotPinkDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Browse our services and add something special",
            style = MaterialTheme.typography.bodyMedium,
            color = Pink80,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                navController.navigate("services") {
                    popUpTo("home") { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = HotPink,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Explore Services")
        }
    }
}

@Composable
private fun CartItemCard(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Pink10)
                ) {
                    if (cartItem.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = cartItem.imageUrl,
                            contentDescription = cartItem.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = "Service Image",
                            tint = HotPink,
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        cartItem.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Display duration
                    cartItem.duration?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.labelSmall,
                            color = Pink80
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "₹${cartItem.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = HotPink
                        )

                        if (cartItem.originalPrice != null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "₹${cartItem.originalPrice}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Total price for this item
                    val itemTotal = cartItem.price * cartItem.quantity
                    Text(
                        "Total: ₹${"%.2f".format(itemTotal)}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = HotPinkDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Quantity selector
                Column {
                    Text("Quantity:", style = MaterialTheme.typography.labelSmall, color = HotPinkDark)
                    QuantitySelector(
                        quantity = cartItem.quantity,
                        onQuantityChange = onQuantityChange
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Remove button
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = HotPink
                    )
                }
            }
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Pink20),
        modifier = Modifier.height(32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Decrease",
                    tint = if (quantity > 1) HotPink else Color.LightGray
                )
            }

            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = HotPinkDark,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = { onQuantityChange(quantity + 1) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = HotPink
                )
            }
        }
    }
}

@Composable
private fun PriceRow(
    label: String,
    amount: Double,
    color: Color = Color.Gray,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = fontWeight
        )
        Text(
            "₹${"%.2f".format(amount)}",
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = fontWeight
        )
    }
}

private fun calculateSubtotal(items: List<CartItem>): Double {
    return items.sumOf { it.price * it.quantity }
}