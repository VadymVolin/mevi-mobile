package com.mevi.ui.components.tabs

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mevi.ui.R
import com.mevi.ui.theme.MeviTheme

@Composable
fun Tabs(
    selectedTabIndex: Int,
    tabTitlesId: List<Int>,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(it[selectedTabIndex]), width = 16.dp
            )
        }) {
        tabTitlesId.forEachIndexed { index, titleId ->
            Tab(text = {
                Text(
                    text = stringResource(id = titleId), style = MaterialTheme.typography.labelLarge
                )
            },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                selected = selectedTabIndex == index,
                onClick = { onTabClick(index) })
        }
    }
}


@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    showSystemUi = true
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun TabsPreview() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    fun onTabClick(index: Int) {
        selectedTabIndex = index
    }
    val tabTitles = listOf( R.string.TEXT_SIGN_IN, R.string.TEXT_SIGN_UP)
    MeviTheme {
        Tabs(selectedTabIndex,
            tabTitles,
            onTabClick = { onTabClick(it) },
        )
    }
}