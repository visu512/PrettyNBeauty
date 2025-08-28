//package com.beauty.parler.navhost
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.expandVertically
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.shrinkVertically
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.beauty.parler.activity.*
//import com.beauty.parler.model.PackageViewModel
//import com.beauty.parler.shared.*
//import com.beauty.parler.ui.theme.HotPink
//import com.beauty.parler.ui.theme.Pink10
//import com.beauty.parler.ui.theme.Pink40
//import com.beauty.parler.ui.theme.Pink80
//
//private data class NavItem(
//    val route: String,
//    val unselectedIcon: ImageVector,
//    val selectedIcon: ImageVector
//)
//
//@SuppressLint("NewApi")
//@Composable
//fun NavHostScreen() {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//
//    val cartViewModel: CartViewModel = viewModel(
//        factory = CartViewModelFactory(CartRepository(context))
//    )
//    val packageViewModel: PackageViewModel = viewModel()
//
//    //  current route to control bottom bar visibility
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//    val bottomBarRoutes = listOf("home", "services", "offers", "cart", "profile")
//    val showBottomBar = currentRoute in bottomBarRoutes
//
//    Scaffold(
//        bottomBar = { if (showBottomBar) BeautyBottomBar(navController, cartViewModel) },
//        containerColor = Pink10
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = "home",
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable("home") {
//                HomeScreen(navController, cartViewModel)
//            }
//            composable("services") {
//                ServiceScreen(navController, cartViewModel, packageViewModel)
//            }
//            composable("services/{packageId}") { backStackEntry ->
//                val packageId = backStackEntry.arguments?.getString("packageId") ?: ""
//                ServiceScreen(navController, cartViewModel, packageViewModel)
//            }
//            composable("offers") { OffersScreen(navController) }
//            composable("cart") { CartScreen(navController, cartViewModel) }
//            composable("profile") { ProfileScreen(navController) }
//        }
//    }
//}
//
//
//@Composable
//private fun BeautyBottomBar(
//    navController: NavController,
//    cartViewModel: CartViewModel
//) {
//    val items = listOf(
//        NavItem("home", Icons.Outlined.Home, Icons.Filled.Home),
//        NavItem("services", Icons.Outlined.Spa, Icons.Filled.Spa),
//        NavItem("offers", Icons.Outlined.LocalOffer, Icons.Filled.LocalOffer),
//        NavItem("cart", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart),
//        NavItem("profile", Icons.Outlined.Person, Icons.Filled.Person),
//    )
//
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//    val cartItemCount by cartViewModel.itemCount.collectAsState()
//
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(70.dp),
//        color = Color.White,
//        shadowElevation = 5.dp,
//        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            items.forEach { item ->
//                val selected = currentRoute == item.route
//
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .clickable {
//                            if (currentRoute != item.route) {
//                                navController.navigate(item.route) {
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            }
//                        }
//                ) {
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier.size(30.dp)
//                    ) {
//                        if (selected) {
//                            Box(
//                                modifier = Modifier
//                                    .size(24.dp)
//                                    .clip(CircleShape)
//                                    .background(HotPink.copy(alpha = 0.2f))
//                            )
//                        }
//
//                        if (item.route == "cart") {
//                            BadgedBox(
//                                badge = {
//                                    if (cartItemCount > 0) {
//                                        Badge {
//                                            Text(
//                                                text = if (cartItemCount > 9) "9+" else cartItemCount.toString(),
//                                                color = Color.White,
//                                                fontSize = 10.sp
//                                            )
//                                        }
//                                    }
//                                }
//                            ) {
//                                Icon(
//                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
//                                    contentDescription = item.route,
//                                    tint = if (selected) Pink80 else Pink40.copy(alpha = 0.5f),
//                                    modifier = Modifier.size(if (selected) 24.dp else 24.dp)
//                                )
//                            }
//                        } else {
//                            Icon(
//                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
//                                contentDescription = item.route,
//                                tint = if (selected) Pink80 else Pink40.copy(alpha = 0.5f),
//                                modifier = Modifier.size(if (selected) 24.dp else 24.dp)
//                            )
//                        }
//                    }
//
//                    AnimatedVisibility(
//                        visible = selected,
//                        enter = fadeIn() + expandVertically(),
//                        exit = fadeOut() + shrinkVertically()
//                    ) {
//                        Text(
//                            text = item.route.replaceFirstChar { it.uppercase() },
//                            color = Pink80,
//                            fontSize = 12.sp,
//                            modifier = Modifier.padding(top = 1.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}






























package com.beauty.parler.navhost

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beauty.parler.activity.*
import com.beauty.parler.model.PackageViewModel
import com.beauty.parler.shared.*
import com.beauty.parler.ui.theme.HotPink
import com.beauty.parler.ui.theme.Pink10
import com.beauty.parler.ui.theme.Pink40
import com.beauty.parler.ui.theme.Pink80

private data class NavItem(
    val route: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

@SuppressLint("NewApi")
@Composable
fun NavHostScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(CartRepository(context))
    )
    val packageViewModel: PackageViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBarRoutes = listOf("home", "services", "offers", "cart", "profile")
    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = { if (showBottomBar) BeautyBottomBar(navController, cartViewModel) },
        containerColor = Pink10,
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // remove default insets
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding) // fix overlap issue
        ) {
            composable("home") {
                HomeScreen(navController, cartViewModel)
            }
            composable("services") {
                ServiceScreen(navController, cartViewModel, packageViewModel)
            }
            composable("services/{packageId}") { backStackEntry ->
                val packageId = backStackEntry.arguments?.getString("packageId") ?: ""
                ServiceScreen(navController, cartViewModel, packageViewModel)
            }
            composable("offers") { OffersScreen(navController) }
            composable("cart") { CartScreen(navController, cartViewModel) }
            composable("profile") { ProfileScreen(navController) }
        }
    }
}

@Composable
private fun BeautyBottomBar(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val items = listOf(
        NavItem("home", Icons.Outlined.Home, Icons.Filled.Home),
        NavItem("services", Icons.Outlined.Spa, Icons.Filled.Spa),
        NavItem("offers", Icons.Outlined.LocalOffer, Icons.Filled.LocalOffer),
        NavItem("cart", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart),
        NavItem("profile", Icons.Outlined.Person, Icons.Filled.Person),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val cartItemCount by cartViewModel.itemCount.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars) // system nav bar
            .height(56.dp),
        color = Color.White,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(30.dp)
                    ) {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(HotPink.copy(alpha = 0.2f))
                            )
                        }

                        if (item.route == "cart") {
                            BadgedBox(
                                badge = {
                                    if (cartItemCount > 0) {
                                        Badge {
                                            Text(
                                                text = if (cartItemCount > 9) "9+" else cartItemCount.toString(),
                                                color = Color.White,
                                                fontSize = 10.sp
                                            )
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.route,
                                    tint = if (selected) Pink80 else Pink40.copy(alpha = 0.5f),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.route,
                                tint = if (selected) Pink80 else Pink40.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = selected,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Text(
                            text = item.route.replaceFirstChar { it.uppercase() },
                            color = Pink80,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 1.dp)
                        )
                    }
                }
            }
        }
    }
}
