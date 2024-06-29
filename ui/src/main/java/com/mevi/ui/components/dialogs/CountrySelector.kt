package com.mevi.ui.components.dialogs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mevi.common.utils.Country
import com.mevi.common.utils.getCountries
import com.mevi.ui.R
import com.mevi.ui.components.HighlightedText
import com.mevi.ui.components.buttons.MeviIconButton

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CountrySelector(
    selectedCountry: Country?,
    onSelectCountry: (Country) -> Unit,
    onDismissRequest: (() -> Unit)? = null
) {
    val searchText = rememberSaveable { mutableStateOf("") }
    val countries = remember { mutableStateOf(getCountries()) }

    val filteredCountries = remember(searchText.value) {
        mutableStateOf(
            if (searchText.value.isEmpty()) {
                countries.value
            } else {
                ArrayList(countries.value.filter {
                    it.countryName.contains(
                        searchText.value,
                        ignoreCase = true
                    )
                })
            }
        )
    }
    Column(
        Modifier
            .fillMaxWidth()

    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            value = searchText.value,
            onValueChange = { searchValue ->
                searchText.value = searchValue
                Log.d("filteredCountries.value", filteredCountries.value.toString())
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.TEXTFIELD_PLACEHOLDER_ENTER_COUNTRY_NAME),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            leadingIcon = {
                MeviIconButton(onClick = {
                    if (onDismissRequest != null) {
                        onDismissRequest()
                    }
                }, icon = Icons.AutoMirrored.Filled.ArrowBack)
            },
            trailingIcon = {
                MeviIconButton(onClick = { searchText.value = "" }, icon = Icons.Default.Clear)
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (filteredCountries.value.isEmpty()) {
            NoMatchesSection()
        } else {
            Text(
                text = if (searchText.value.isEmpty()) "All countries".uppercase() else "Matches".uppercase(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
            ) {
                items(filteredCountries.value, itemContent = { country ->
                    CountryListItem(
                        flag = country.flag,
                        countryName = country.countryName,
                        searchString = searchText.value,
                        onClick = { onSelectCountry(country) },
                        selectedCountry = selectedCountry
                    )
                })
            }
        }

    }
}

@Composable
fun CountryListItem(
    flag: String,
    countryName: String,
    searchString: String,
    onClick: () -> Unit,
    selectedCountry: Country?
) {
    val isSelected = selectedCountry?.countryName == countryName
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier, text = flag, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(12.dp))
            HighlightedText(
                modifier = Modifier.weight(1f),
                fullText = countryName,
                searchString = searchString
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    }

}

@Composable
fun NoMatchesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "No matches", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Oops, it looks like there is no such country",
            style = MaterialTheme.typography.titleSmall
        )
    }
}