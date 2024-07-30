package com.mevi.ui.components.dialogs

import DialogListItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mevi.ui.R
import com.mevi.ui.model.GenderModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun GenderSelector(
    selectedGender: GenderModel?,
    onSelectGender: (GenderModel) -> Unit,
) {
    val genderList: ArrayList<GenderModel> =
        arrayListOf(
            GenderModel(
                type = stringResource(R.string.TEXT_GENDER_MALE),
                icon = Icons.Default.Male
            ),
            GenderModel(
                type = stringResource(R.string.TEXT_GENDER_FEMALE),
                icon = Icons.Default.Female
            )
        )

    Column(
        Modifier
            .height(100.dp)
            .padding(8.dp)

    ) {
        genderList.forEachIndexed { index, gender ->
            Row(modifier = Modifier.weight(1f)) {
                DialogListItem(
                    icon = gender.icon,
                    name = gender.type,
                    onClick = {
                        onSelectGender(gender)
                    },
                    selectedItem = selectedGender?.type
                )
            }
            if (index < genderList.size - 1) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }


    }
}