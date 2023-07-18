package io.github.acodili.jg.still_clouds.util;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class EaseTest {
    private static final Rectangle2D GRAPH_BOUNDS = new Rectangle2D.Double(0.0, -0.5, 1.0 - 0.0,
            1.5 - (-0.5));

    private static final Stroke GRAPH_STROKE = new BasicStroke(25.0f, CAP_ROUND, JOIN_ROUND);

    private static final Path OUTPUT_PARENT_PATH = Path.of("build", "custom", "ease", "graph")
            .toAbsolutePath();

    private static final int PIXELS_PER_VIEWPORT_UNIT = 1000 / 1;

    private static final double VIEWPORT_UNITS_PER_PIXEL = 0.001 / 1;

    @ParameterizedTest
    @EnumSource(Ease.class)
    void graph(final Ease ease) {
        final var image = new BufferedImage(
                (int) (GRAPH_BOUNDS.getWidth() * PIXELS_PER_VIEWPORT_UNIT),
                (int) (GRAPH_BOUNDS.getHeight() * PIXELS_PER_VIEWPORT_UNIT),
                BufferedImage.TYPE_BYTE_GRAY);

        // Build Graph
        final var points = IntStream.rangeClosed(0, image.getWidth())
                .parallel()
                .mapToObj(i -> {
                    final var pixelX = i - 0.5;
                    final var graphX = pixelX * VIEWPORT_UNITS_PER_PIXEL + GRAPH_BOUNDS.getMinX();
                    final var graphY = ease.apply(graphX);
                    final var pixelY = (1.0 - graphY - GRAPH_BOUNDS.getMinY()) * PIXELS_PER_VIEWPORT_UNIT + 0.5;

                    return new Point2D.Double(pixelX, pixelY);
                })
                .toArray(Point2D.Double[]::new);

        // Render Graph
        final var imageGraphics = image.createGraphics();

        imageGraphics.setColor(WHITE);
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        imageGraphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        imageGraphics.setStroke(GRAPH_STROKE);

        final var line = new Line2D.Double();

        imageGraphics.setColor(GRAY);

        final var xAtZero = (1.0 - (0.0 - GRAPH_BOUNDS.getMinY())) * PIXELS_PER_VIEWPORT_UNIT;
        line.setLine(0.0, xAtZero, image.getWidth(), xAtZero);
        imageGraphics.draw(line);

        final var xAtOne = (1.0 - (1.0 - GRAPH_BOUNDS.getMaxY())) * PIXELS_PER_VIEWPORT_UNIT;
        line.setLine(0.0, xAtOne, image.getWidth(), xAtOne);
        imageGraphics.draw(line);

        imageGraphics.setColor(BLACK);

        for (var i = 0; i < image.getWidth(); i++) {
            line.setLine(points[i], points[i + 1]);
            imageGraphics.draw(line);
        }

        imageGraphics.dispose();

        // Export Graph
        assertDoesNotThrow(() -> {
            Files.createDirectories(OUTPUT_PARENT_PATH);

            final var outputPath = Files.newOutputStream(
                    OUTPUT_PARENT_PATH.resolve(ease.ordinal() + ". " + ease + ".png"),
                    StandardOpenOption.CREATE);

            ImageIO.write(image, "png", outputPath);
        });
    }
}
