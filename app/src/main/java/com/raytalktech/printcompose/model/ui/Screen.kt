package com.raytalktech.printcompose.model.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailPersonOrders : Screen("home/{idReceiverOrder}") {
        fun createRouteDetailPersonOrders(idReceiverOrder: Long) = "home/$idReceiverOrder"
        fun createRouteDetailOrderItems(idOrderItem: Long) = "home/$idOrderItem"
    }


    object Admin : Screen("admin")
    object DetailStore : Screen("admin/{idAdminMenu}") {
        fun createRouteDetailStore(idAdminMenu: Int) = "admin/$idAdminMenu"
    }

    object NewOrderItem : Screen("neworder/{idReceiver}") {
        fun createRouteNewOrderItem(idReceiver: Long) = "neworder/$idReceiver"
    }

    object Print : Screen("printing")
    //fun getOrderData(idReceiverOrder: Long) = "print/$idReceiverOrder"

}

/**
 * To handle Home BottomBar
 */
fun isNeedHomeBottomBar(route: String?): Boolean {
    return listOf(
        Screen.Home.route,
        Screen.Admin.route
    ).contains(route)
}