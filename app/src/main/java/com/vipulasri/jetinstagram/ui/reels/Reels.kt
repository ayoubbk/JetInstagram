package com.vipulasri.jetinstagram.ui.reels

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.vipulasri.jetinstagram.R
import com.vipulasri.jetinstagram.data.PostsRepository
import com.vipulasri.jetinstagram.model.Post
import com.vipulasri.jetinstagram.ui.components.defaultPadding
import com.vipulasri.jetinstagram.ui.components.horizontalPadding
import com.vipulasri.jetinstagram.ui.components.icon
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Reels() {
  Stack(modifier = Modifier.background(color = Color.Black)) {
    ReelsList()
    ReelsHeader()
  }
}

@Composable
private fun ReelsHeader() {
  Row(
      modifier = Modifier.fillMaxWidth()
          .defaultPadding(),
      horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text("Reels", color = Color.White)
    Icon(
        imageResource(id = R.drawable.ic_outlined_camera),
        tint = Color.White,
        modifier = Modifier.icon()
    )
  }
}

@Composable
private fun ReelsList() {
  val posts by PostsRepository.observePosts()

  LazyColumnFor(items = posts) { post ->
    Stack(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(0.56f),
    ) {
      CoilImage(
          data = post.image,
          contentScale = ContentScale.Fit,
          loading = {
            LoadingIndicator(
                modifier = Modifier.fillParentMaxSize()
            )
          },
          modifier = Modifier.fillParentMaxSize(),
      )
      Column(
          modifier = Modifier.gravity(Alignment.BottomStart),
      ) {
        ReelFooter(post = post, modifier = Modifier)
        Divider()
      }
    }
  }
}

@Composable
private fun ReelFooter(
  modifier: Modifier,
  post: Post
) {
  Column(modifier) {

    // user data
    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.defaultPadding()
    ) {
      CoilImage(
          data = post.userImage,
          contentScale = ContentScale.Crop,
          modifier = Modifier.preferredSize(20.dp)
              .background(color = Color.Gray, shape = CircleShape)
              .clip(CircleShape)
      )
      Spacer(modifier = Modifier.width(horizontalPadding))
      Text(
          text = post.userName,
          color = Color.White,
          style = MaterialTheme.typography.subtitle2
      )
      Spacer(modifier = Modifier.width(horizontalPadding))
      Canvas(modifier = Modifier.preferredSize(5.dp), onDraw = {
        drawCircle(
            color = Color.White,
            radius = 8f
        )
      })
      Spacer(modifier = Modifier.width(horizontalPadding))
      Text(
          text = "Follow",
          color = Color.White,
          style = MaterialTheme.typography.subtitle2
      )
    }

    // actions
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalGravity = Alignment.CenterVertically
    ) {
      Row(
          verticalGravity = Alignment.CenterVertically
      ) {
        UserAction(R.drawable.ic_outlined_favorite)
        Spacer(modifier = Modifier.width(horizontalPadding))
        UserAction(R.drawable.ic_outlined_comment)
        Spacer(modifier = Modifier.width(horizontalPadding))
        UserAction(R.drawable.ic_dm)
      }

      Row(
          verticalGravity = Alignment.CenterVertically
      ) {
        UserActionWithText(
            drawableRes = R.drawable.ic_outlined_favorite,
            text = post.likesCount.toString()
        )
        Spacer(modifier = Modifier.width(horizontalPadding))
        UserActionWithText(
            drawableRes = R.drawable.ic_outlined_favorite,
            text = post.commentsCount.toString()
        )
      }
    }

  }
}

@Composable
private fun UserAction(@DrawableRes drawableRes: Int) {
  Icon(
      imageResource(id = drawableRes),
      tint = Color.White,
      modifier = Modifier.icon()
  )
}

@Composable
private fun UserActionWithText(
  @DrawableRes drawableRes: Int,
  text: String
) {
  Icon(
      imageResource(id = drawableRes),
      tint = Color.White,
      modifier = Modifier.preferredSize(18.dp)
  )
  Spacer(modifier = Modifier.width(horizontalPadding / 2))
  Text(
      text = text,
      color = Color.White,
      style = MaterialTheme.typography.body2
  )
}

@Composable
private fun LoadingIndicator(modifier: Modifier) {
  Box(
      modifier = modifier,
      gravity = ContentGravity.Center
  ) {
    CircularProgressIndicator(
        color = Color.Gray,
        strokeWidth = 2.dp
    )
  }
}