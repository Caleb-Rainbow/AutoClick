package com.auto.click.ui.common

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * 带有必选符号的输入框
 * @param hint 提示文字
 * @param isRequired 是否必选
 * @param value 输入框的值
 * @param onValueChanged 输入框的值改变的回调
 * */
@Composable
fun RequiredOutlinedTextField(
    modifier: Modifier = Modifier,
    hint: String,
    isRequired: Boolean,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChanged: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = {
            Text(text = buildAnnotatedString {
                if (isRequired) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("*")
                    }
                }
                append(hint)
            }, fontSize = 14.sp)
        },
        textStyle = TextStyle.Default.copy(fontSize = 14.sp),
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        singleLine = singleLine,
        onValueChange = {
            try {
                onValueChanged(it)
            } catch (e: NumberFormatException) {
                e.message?.let { it1 -> Log.e("NumberFormatException", it1) }
            }
        }
    )
}

@Composable
fun CheckboxAndText(modifier: Modifier = Modifier,text:String,checked:Boolean,onCheckedChange: (Boolean) -> Unit){
    Row(modifier = modifier.toggleable(value = checked, onValueChange = onCheckedChange, role = Role.Checkbox), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = null)
        Text(modifier = Modifier.padding(vertical = 5.dp), text = text)
    }
}

@Composable
fun WidthSpace(width: Dp){
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun HeightSpace(height: Dp){
    Spacer(modifier = Modifier.height(height))
}