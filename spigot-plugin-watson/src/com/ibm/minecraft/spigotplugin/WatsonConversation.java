package com.ibm.minecraft.spigotplugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.Context;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class WatsonConversation extends JavaPlugin {
    Conversation conversationService = null;
    Context context = null;

    static String USER_ID = null;
    static String USER_PASS = null;
    static String SERVICE_ENDPOINT = null;
    static String WORKSPACE_ID = null;

    static {
        System.out.println("NEW INSTANCE");
        try (InputStream is = WatsonConversation.class.getResourceAsStream("conversationservice.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            USER_ID = properties.getProperty("userid", "");
            USER_PASS = properties.getProperty("userpass", "");
            SERVICE_ENDPOINT = properties.getProperty("service_endpoint", "");
            WORKSPACE_ID = properties.getProperty("workspace_id", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
        // getLogger().info("WatsonSpigotPlugin");
        conversationService = setupConversationService();
    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // getLogger().info("command: " + cmd.getName());
        // getServer().dispatchCommand(getServer().getConsoleSender(), cmd.getName());
        if (cmd.getName().equalsIgnoreCase("hello")) {
            sender.sendMessage("Welcome to Watson");
        }
        if (cmd.getName().equalsIgnoreCase("watson")) {
            if (args.length == 0) {
                sender.sendMessage("WATSON Rocks!!");
                return true;
            }
            if (args.length >= 1) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    str.append(args[i]);
                    if (i < args.length - 1) {
                        str.append(" ");
                    }
                }
                String input = str.toString();
                
                //getLogger().info(input);
                String response = getResponse(input, conversationService, this.context);
                //getLogger().info(response);
                sender.sendMessage("Watson response: " + response );

                return true;
            }
        }
        return false;
    }

    public Conversation setupConversationService() {
        Conversation service = new Conversation(Conversation.VERSION_DATE_2017_05_26);
        // the user name and password from the VCAP_SERVICES env variable of the Conversation service instance
        service.setUsernameAndPassword(USER_ID, USER_PASS);
        service.setEndPoint(SERVICE_ENDPOINT);

        return service;
    }

    public String getResponse(String input, Conversation service, Context context) {
        // Create conversation
        if (context == null) {
            context = new Context();
            System.out.println("NEW CONTEXT");
        }
        InputData inputData = new InputData.Builder(input).build();
        MessageOptions options = new MessageOptions.Builder(WORKSPACE_ID)
                .context(context)
                .input(inputData)
                .build();

        // Ask question
        MessageResponse response = service.message(options).execute();
        this.context = response.getContext();
        System.out.println("CONTEXT " + context.getConversationId());

        List<String> responseList = response.getOutput().getText();
        String resposeString = "";
        for (String string : responseList) {
            resposeString = resposeString + System.lineSeparator() + string;
        }
        
        return resposeString;
    }

}
