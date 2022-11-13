package social.androiddev.timeline

//import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import social.androiddev.common.theme.MastodonTheme

@Composable
fun TootContent(
    modifier: Modifier,
    username: String,
    userAddress: String,
    message: String?,
    date: String,
    videoUrl: String?,
    images: List<String>,
) {
    Column(
        modifier = modifier,
    ) {
        TootContentHeader(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            username = username,
            userAddress = userAddress,
            date = date,
        )
        VerticalSpacer()
        // TODO Add support for video + multiple images rendering
        // for now just show message from toot
        if (message != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message,
                style = MaterialTheme.typography.caption
            )
            VerticalSpacer()
        }
    }
}

//@Preview
@Composable
private fun PreviewTootContentLight() {
    MastodonTheme(useDarkTheme = false) {
        Surface {
            TootContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                username = "@Omid",
                userAddress = "@omid@androiddev.social",
                message = "\uD83D\uDC4BHello #AndroidDev",
                date = "1d",
                images = emptyList(),
                videoUrl = null
            )
        }
    }
}

//@Preview
@Composable
private fun PreviewTootContentDark() {
    MastodonTheme(useDarkTheme = true) {
        Surface {
            TootContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                username = "@Omid",
                userAddress = "@omid@androiddev.social",
                message = "\uD83D\uDC4BHello #AndroidDev",
                date = "1d",
                images = emptyList(),
                videoUrl = null
            )
        }
    }
}