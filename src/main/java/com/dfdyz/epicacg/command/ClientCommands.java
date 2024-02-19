package com.dfdyz.epicacg.command;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.particle.DMC.SpaceBrokenParticle;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.registry.MyModels;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;

@OnlyIn(Dist.CLIENT)
public class ClientCommands {

    public static void MSGClient(String str){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.displayClientMessage(Component.nullToEmpty(str),false);
        }
    }

    protected static LiteralArgumentBuilder<CommandSourceStack> command;

    public static void Debug(){
        try{
            //ParticleRenderingPhase phase = ReflectionUtils.GetField(Minecraft.getInstance().particleEngine, "phase");
            //System.out.println(phase);

            /*
            MyModels.LoadOtherModel();
            ClientLevel level = Minecraft.getInstance().level;
            Vec3 pos = Minecraft.getInstance().player.position();
            RenderUtils.AddParticle(level, new SpaceBrokenParticle(level, pos.x, pos.y, pos.z, 0));

            RenderUtils.AddParticle(level, new SpaceBrokenParticle(level, pos.x, pos.y, pos.z, 1));
*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        command = Commands.literal(EpicACG.MODID).executes(context -> {
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null) {
                        player.displayClientMessage(Component.nullToEmpty("Function:\nReload\nOption:\nGenShinVoice <boolean>\nDeathParticle <boolean>"),false);
                    }

                    Debug();

                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("Reload")
                        .executes(context -> {
                            ClientConfig.Load(true);
                            //BladeTrailTextureLoader.ReleaseAll();
                            //BladeTrailTextureLoader.Load();
                            MyAnimations.LoadCamAnims();
                            MSGClient("[EpicACG]Reload All Config.");
                            return Command.SINGLE_SUCCESS;
                        }))
                /*
                .then(Commands.literal("SwordTrail")
                        .executes(context -> {
                            MSGClient("[EpicACG]SwordTrail Render: " + ClientConfig.cfg.EnableSwordTrail);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                 */
                .then(Commands.literal("DeathParticle")
                        .executes(context -> {
                            MSGClient("[EpicACG]DeathParticle: " + ClientConfig.cfg.EnableDeathParticle);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("GenShinVoice")
                        .executes(context -> {
                            MSGClient("[EpicACG]GenShinVoice: " + (ClientConfig.cfg.EnableGenShinVoice ? "Enable" : "Disable"));
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                /*
                .then(Commands.literal("HealthBar")
                        .executes(context -> {
                            MSGClient("[EpicACG]HealthBar Render: " + ClientConfig.cfg.EnableHealthBar);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("RenderSelf")
                                .executes(context -> {
                                    MSGClient("[EpicACG]Rendering Health Bar for self: " + ClientConfig.cfg.RenderHealthBarSelf);
                                    return Command.SINGLE_SUCCESS;
                                }).then(Commands.literal("true")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = true;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicACG]Enabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        }))
                                .then(Commands.literal("false")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = false;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicACG]Disabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(Commands.literal("OptFineMode")
                        .executes(context -> {
                            MSGClient("[EpicACG]OptFineMode: " + ClientConfig.cfg.EnableOptFineMode);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Enabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicACG]Disabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                })))

                 */
        ;



        dispatcher.register(command);
    }


}
