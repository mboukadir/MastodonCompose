/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.signedout.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import social.androiddev.common.composables.webview.SignInSignUpWebView
import social.androiddev.signedout.navigation.SignInSignUpComponent

@Composable
fun SignInSignUpContent(
    server: String,
    component: SignInSignUpComponent,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
    ) {

        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text("Sign In / Sign Up")
            },
            navigationIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        component.onCloseClicked()
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                )
            }
        )

        SignInSignUpWebView(
            url = "https://$server/oauth/authorize?client_id=MY_CLIENT_ID&redirect_uri=https://dodomastodon.com&response_type=code&scope=read",
            onSuccess = { component.onSignInSignUpSucceed() },
            onFailure = { component.onSignInSignUpFailed() }
        )
    }
}
