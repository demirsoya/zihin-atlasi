package com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.convexBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 4.dp,
    convexStyle: ConvexStyle = ConvexStyle()
) = this.drawWithContent {
    // Adjust the size to fit within canvas boundaries
    val adjustedSize = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx())
    // Create an outline based on the shape and adjusted size
    val outline = shape.createOutline(adjustedSize, layoutDirection, this)

    // Draw the original content of the composable
    drawContent()
    val halfStrokeWidth = strokeWidth.toPx() / 2
    // Translate the canvas to fit the border within its bounderies
    translate(halfStrokeWidth, halfStrokeWidth) {
        // Draw main border outline
        drawOutline(
            outline = outline,
            color = color,
            style = Stroke(width = strokeWidth.toPx())
        )
    }

    with(convexStyle) {
        // Draw the shadow outline
        drawConvexBorderShadow(outline, strokeWidth, blur, -offset, -offset, shadowColor)
        // Draw the glare outline
        drawConvexBorderShadow(outline, strokeWidth, blur, offset, offset, glareColor)
    }
}


fun DrawScope.drawConvexBorderShadow(
    outline: Outline,
    strokeWidth: Dp,
    blur: Dp,
    offsetX: Dp,
    offsetY: Dp,
    shadowColor: Color
) = drawIntoCanvas { canvas ->
    // Create and set up a Paint object
    val shadowPaint = Paint().apply {
        this.style = PaintingStyle.Stroke
        this.color = shadowColor
        this.strokeWidth = strokeWidth.toPx()
    }

    // Save the current layer before transformations
    canvas.saveLayer(size.toRect(), shadowPaint)

    val halfStrokeWidth = strokeWidth.toPx() / 2
    // Translate the canvas to fit the border within its bounderies
    canvas.translate(halfStrokeWidth, halfStrokeWidth)
    // Draw the shadow outline
    canvas.drawOutline(outline, shadowPaint)

    // Apply blending mode and blur effect for the shadow
    shadowPaint.asFrameworkPaint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
    }
    // Set the color for clipping
    shadowPaint.color = Color.Black

    // Translate canvas and draw the shadow clipping outline
    canvas.translate(offsetX.toPx(), offsetY.toPx())
    canvas.drawOutline(outline, shadowPaint)
    // Restore canvas to its original state
    canvas.restore()
}

data class ConvexStyle(
    val blur: Dp = 3.dp,
    val offset: Dp = 2.dp,
    val glareColor: Color = Color.White.copy(0.64f),
    val shadowColor: Color = Color.Black.copy(0.64f)
)