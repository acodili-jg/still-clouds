package io.github.acodili.jg.still_clouds.mixin.client;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.acodili.jg.still_clouds.StillCloudsClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;

/**
 * A mix-in class for minecraft's {@link LevelRenderer}.
 */
@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    /**
     * The shadowed field minecraft.
     */
    @Final
    @Shadow
    private Minecraft minecraft;

    /**
     * The shadowed field ticks.
     */
    @Shadow
    private int ticks;

    /**
     * Integrates Still Clouds' engine into Minecraft's cloud rendering by directly modifying a
     * specific variable that appears to be the cloud center in the x-axis.
     *
     * @param cloudsCenterX     the value variable being modified
     * @param poseStack        the {@code 0}<sup>th</sup> parameter of the method containing the
     *                         modified variable
     * @param projectionMatrix the {@code 1}<sup>st</sup> parameter
     * @param partialTick      the {@code 2}<sup>nd</sup> parameter
     * @param cameraX          the {@code 3}<sup>rd</sup> parameter
     * @param cameraY          the {@code 4}<sup>th</sup> parameter
     * @param cameraZ          the {@code 5}<sup>th</sup> parameter
     * @return the new value for the variable being modified
     */
    @ModifyVariable(
            method = "renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FDDD)V",
            at = @At(value = "STORE"),
            index = 10,
            ordinal = 4,
            name = "l")
    private double modifyCloudCenter(final double cloudsCenterX, final PoseStack poseStack,
            final Matrix4f projectionMatrix, final float partialTick, final double cameraX,
            final double cameraY, final double cameraZ) {
        final var engine = StillCloudsClient.getInstance().getEngine();

        return engine.calculateReposition(cameraX, this.ticks + partialTick, cloudsCenterX);
    }
}