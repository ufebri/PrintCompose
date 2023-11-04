package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raytalktech.printcompose.R
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDefault(title: String, navigationClick: () -> Unit?, icon: ImageVector?) {
    TopAppBar(
        title = {
            Text(
                text = title, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier.clickable { navigationClick() }
                )
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    title: String,
    icNav: ImageVector?,
    onClickNav: () -> Unit?,
    icAction: ImageVector?,
    onClickAction: () -> Unit?
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onClickNav() }) {
                if (icNav != null) {
                    Icon(
                        imageVector = icNav,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { onClickAction() }) {
                if (icAction != null) {
                    Icon(
                        imageVector = icAction,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
    )
}


@Composable
@Preview(showBackground = true)
private fun PreviewTopBar() {
    PrintComposeTheme {
        TopBarDefault(title = "Title", {}, Icons.Filled.ArrowBack)
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewCenterTopBar() {
    PrintComposeTheme {
        CenteredAppBar(
            title = "Title",
            Icons.Filled.ArrowBack,
            {},
            ImageVector.vectorResource(id = R.drawable.ic_print),
            {})
    }
}