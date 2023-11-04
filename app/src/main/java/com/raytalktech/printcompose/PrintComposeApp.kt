package com.raytalktech.printcompose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raytalktech.printcompose.model.ui.NavigationItem
import com.raytalktech.printcompose.model.ui.Screen
import com.raytalktech.printcompose.model.ui.isNeedHomeBottomBar
import com.raytalktech.printcompose.ui.component.FabAddReceiverOrder
import com.raytalktech.printcompose.ui.component.TopBarDefault
import com.raytalktech.printcompose.ui.screens.add.DetailPersonOrdersScreen
import com.raytalktech.printcompose.ui.screens.admin.AdminScreen
import com.raytalktech.printcompose.ui.screens.admin.StoreInformationScreen
import com.raytalktech.printcompose.ui.screens.home.HomeScreen
import com.raytalktech.printcompose.ui.screens.neworder.NewOrderScreen
import com.raytalktech.printcompose.ui.screens.print.BluetoothPrinterScreen
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme
import com.raytalktech.printcompose.util.BluetoothPrinterHelper

@Composable
private fun BottomBar(
    navHostController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navigationItems = listOf(
            NavigationItem("Home", icon = Icons.Default.Home, screen = Screen.Home),
            NavigationItem("Admin", icon = Icons.Default.Build, screen = Screen.Admin)
        )

        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.map { item ->
            NavigationBarItem(selected = currentRoute == item.screen.route,
                onClick = {
                    navHostController.navigate(item.screen.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintComposeApp(
    modifier: Modifier = Modifier, navHostController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(topBar = {
        if (currentRoute == Screen.Home.route) TopBarDefault(
            stringResource(R.string.app_name),
            {},
            null
        )
    }, bottomBar = {
        if (isNeedHomeBottomBar(currentRoute)) BottomBar(navHostController = navHostController)
    }, modifier = modifier, floatingActionButton = {
        if (currentRoute == Screen.Home.route) FabAddReceiverOrder()
    }) { padding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigate = { idReceiverOrder ->
                    navHostController.navigate(
                        Screen.DetailPersonOrders.createRouteDetailPersonOrders(idReceiverOrder)
                    )
                })
            }

            composable(
                Screen.DetailPersonOrders.route,
                arguments = listOf(navArgument("idReceiverOrder") { type = NavType.LongType })
            ) {
                val idReceiverOrder = it.arguments?.getLong("idReceiverOrder") ?: -1L
                DetailPersonOrdersScreen(idReceiverOrder = idReceiverOrder,
                    navigateToCreateNewOrder = { idReceiver ->
                        navHostController.navigate(
                            Screen.NewOrderItem.createRouteNewOrderItem(
                                idReceiver
                            )
                        )
                    },
                    onBackClicked = { navHostController.navigateUp() },
                    onPrintClicked = { navHostController.navigate(Screen.Print.route) })
            }

            composable(
                Screen.NewOrderItem.route,
                arguments = listOf(navArgument("idReceiver") { type = NavType.LongType })
            ) {
                val idReceiverOrder = it.arguments?.getLong("idReceiver") ?: -1L

                NewOrderScreen(idReceiverOrder = idReceiverOrder,
                    onBackClick = { navHostController.navigateUp() },
                    onSaveClicked = { navHostController.popBackStack() })
            }

            composable(
                Screen.Print.route,
               // arguments = listOf(navArgument("idReceiverOrder") { type = NavType.LongType })
            ) {
              //  val idPrint = it.arguments?.getLong("idReceiverOrder") ?: -1L
                BluetoothPrinterScreen(
                    bluetoothPrinterHelper = BluetoothPrinterHelper(LocalContext.current as ComponentActivity),
                    idReceiverOrder = 1
                )
            }

            composable(Screen.Admin.route) {
                AdminScreen(navigate = { idAdminMenu ->
                    if (idAdminMenu != 2) {
                        navHostController.navigate(
                            Screen.DetailStore.createRouteDetailStore(idAdminMenu = idAdminMenu)
                        )
                    }
                })
            }

            composable(
                Screen.DetailStore.route,
                arguments = listOf(navArgument("idAdminMenu") { type = NavType.IntType })
            ) {
                when (it.arguments?.getInt("idAdminMenu") ?: -1) {
                    1 -> StoreInformationScreen(navigationBack = { navHostController.navigateUp() },
                        onSaveClicked = { navHostController.popBackStack() })
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PrintComposeAppPreview() {
    PrintComposeTheme {
        PrintComposeApp()
    }
}