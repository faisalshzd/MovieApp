import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }


    val montserratMedium = FontFamily(Font(R.font.montserrat_medium, FontWeight.Medium))
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF171725))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(horizontal = 24.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(32.dp).background(Color(0xFF252836), RoundedCornerShape(8.dp))
                        .height(28.dp)
                        .width(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp)
                    )

                }
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = montserratSemiBold,
                    modifier = Modifier.weight(1f).padding(end = 32.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(46.dp))

            Text(
                text = "Let's get started",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = montserratSemiBold,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                text = "The latest movies and series\nare here",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Full Name
            Text(
                text = "Full Name",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.padding(start = 18.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = {
                    Text(
                        text = "Enter your full name",
                        color = Color(0xFF92929D),
                        fontSize = 14.sp,
                        fontFamily = montserratMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0xFF252836), RoundedCornerShape(24.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color(0xFF92929D),
                    unfocusedTextColor = Color(0xFF92929D),
                    disabledTextColor = Color.Gray,
                    focusedPlaceholderColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratMedium,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email
            Text(
                text = "Email Address",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.padding(start = 18.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                placeholder = {
                    Text(
                        text = "you@example.com",
                        color = Color(0xFF92929D),
                        fontSize = 14.sp,
                        fontFamily = montserratMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0xFF252836), RoundedCornerShape(24.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color(0xFF92929D),
                    unfocusedTextColor = Color(0xFF92929D),
                    disabledTextColor = Color(0xFF92929D),
                    focusedPlaceholderColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratMedium,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Password
            Text(
                text = "Password",
                color = Color(0xFFEBEBEF),
                fontSize = 12.sp,
                fontFamily = montserratMedium,
                modifier = Modifier.padding(start = 18.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "Enter your password",
                        color = Color(0xFF92929D),
                        fontSize = 14.sp,
                        fontFamily = montserratMedium,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0xFF252836), RoundedCornerShape(24.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color(0xFF92929D),
                    unfocusedTextColor = Color(0xFF92929D),
                    disabledTextColor = Color(0xFF92929D),
                    focusedPlaceholderColor = Color.Transparent,
                    unfocusedPlaceholderColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,

                    ),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = montserratMedium,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisibility) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, contentDescription = description, tint = Color(0xFF92929D))
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Terms Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF12CDD9),
                        uncheckedColor = Color(0xFF92929D),
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier.size(24.dp)
                        .padding(start = 24.dp)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(Color(0xFF92929D), fontFamily = montserratMedium, fontSize = 12.sp)) {
                            append("I agree to the ")
                        }
                        pushStringAnnotation(tag = "TERMS", annotation = "Terms")
                        withStyle(SpanStyle(color = Color(0xFF12CDD9), fontFamily = montserratMedium, fontSize = 12.sp)) {
                            append("Terms and Services")
                        }
                        pop()
                        withStyle(SpanStyle(Color(0xFF92929D), fontFamily = montserratMedium, fontSize = 12.sp)) {
                            append(" and ")
                        }
                        pushStringAnnotation(tag = "PRIVACY", annotation = "Privacy")
                        withStyle(SpanStyle(color = Color(0xFF12CDD9), fontFamily = montserratMedium,  fontSize = 12.sp)) {
                            append("Privacy Policy")
                        }
                        pop()
                    },
                    modifier = Modifier
                        .width(260.dp)
                        .padding(start = 22.dp)
                        .clickable {
                        onTermsClick()
                        onPrivacyClick()
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { /* Sign Up action */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF12CDD9))
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = montserratMedium,
                )
            }
        }
    }
}
