package com.mevi.ui.components.dialogs

import DialogListItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mevi.ui.R
import com.mevi.ui.components.buttons.MeviIconButton
import com.mevi.ui.model.CountryModel
import com.mevi.ui.utils.CountriesUtils

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CountrySelector(
    selectedCountry: CountryModel?,
    onSelectCountry: (CountryModel) -> Unit,
    onDismissRequest: (() -> Unit)? = null
) {
    val searchText = rememberSaveable { mutableStateOf("") }
    val countries = remember { mutableStateOf(CountriesUtils.getCountries()) }

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
            trailingIcon = if (searchText.value.isNotEmpty()) {
                {
                    MeviIconButton(onClick = { searchText.value = "" }, icon = Icons.Default.Clear)
                }
            } else {
                null
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
                text = if (searchText.value.isEmpty()) stringResource(R.string.TEXT_ALL_COUNTRIES).uppercase() else stringResource(
                    R.string.TEXT_MATCHES
                ).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
            ) {
                items(filteredCountries.value, itemContent = { country ->
                    DialogListItem(
                        icon = country.flag,
                        name = country.countryName,
                        searchString = searchText.value,
                        onClick = { onSelectCountry(country) },
                        selectedItem = selectedCountry?.countryName
                    )
                })
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
        Text(
            text = stringResource(R.string.TEXT_NO_MATCHES),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.TEXT_NO_SUCH_COUNTRY),
            style = MaterialTheme.typography.titleSmall
        )
    }
}