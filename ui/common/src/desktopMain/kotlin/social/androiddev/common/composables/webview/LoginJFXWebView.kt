/*
 * This file is part of Dodo.
 *
 * Dodo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Dodo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Dodo. If not, see <https://www.gnu.org/licenses/>.
 */
package social.androiddev.common.composables.webview

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.web.WebView
import java.net.URI

/**
 * From, https://stackoverflow.com/a/26028556
 */
internal class LoginJFXWebView(
    private val url: String,
    private val onSuccess: (code: String) -> Unit,
    private val onFailure: (error: String) -> Unit
) : JFXPanel() {
    init {
        Platform.runLater(::initialiseJavaFXScene)
    }

    private fun initialiseJavaFXScene() {
        val webView = WebView()
        webView.engine.apply {

            webView.engine.locationProperty().addListener { observable, _, _ ->
                val uri = URI(observable.value)
                println("url =${observable.value}")
                println("Uri =$uri")
                println("uri.scheme =${uri.scheme}")
                println("uri.host =${uri.host}")
                if (uri.scheme == "https" && uri.host == "dodomastodon.com") {
                    if (uri.query.contains("error")) {
                        val error = uri.query.replace("error=", "")
                        println("error =$error")
                        onFailure(error)
                    } else {
                        val code = uri.query.replace("code=", "")
                        println("code =$code")
                        onSuccess(code)
                    }
                } else {
                    println("isNote oauth url")
                }
            }
            load(url)
        }

        val scene = Scene(webView)
        setScene(scene)
    }
}
