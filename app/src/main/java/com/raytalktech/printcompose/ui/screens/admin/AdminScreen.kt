package com.raytalktech.printcompose.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raytalktech.printcompose.config.ViewModelFactory
import com.raytalktech.printcompose.di.Injection
import com.raytalktech.printcompose.model.data.AdminData
import com.raytalktech.printcompose.ui.component.TopBarDefault
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    modifier: Modifier = Modifier, viewModel: AdminViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(
                LocalContext.current
            )
        )
    ),
    navigate: (Int) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopBarDefault(title = "Admin", navigationClick = {}, icon = null)
    }) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = modifier
            ) {
                items(viewModel.getAdminMenuList()) { data ->
                    AdminItems(modifier = modifier, adminData = data, navigate = navigate)
                }
            }
        }
    }
}

@Composable
fun AdminItems(modifier: Modifier, adminData: AdminData, navigate: (Int) -> Unit) {
    Card(
        modifier
            .fillMaxWidth()
            .clickable { navigate(adminData.id) }) {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = adminData.ic),
                    contentDescription = "icon",
                    modifier = modifier
                        .size(50.dp, 50.dp)
                        .padding(8.dp),
                )

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = adminData.title,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp
                        )
                    )
                    Text(
                        text = adminData.desc,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAdminScreen() {
    PrintComposeTheme {
        AdminScreen(navigate = {})
    }
}