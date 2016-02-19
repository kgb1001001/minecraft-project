package com.ibm.minecraft.spigotplugin;

//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Conversation;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;


public class WatsonDialog extends JavaPlugin {
	DialogService DIALOG_SERVICE=null;
	//Dialog DIALOG=null;
	Conversation CONVERSATION=null;
	static String USER_ID=null;
	static String USER_PASS=null;
	static String DIALOG_ENDPOINT=null;
	static String DIALOG_ID=null;
	
	static {
		try (InputStream is = WatsonDialog.class.getResourceAsStream("dialog.properties")) {
			Properties properties = new Properties();
			properties.load(is);
			USER_ID = properties.getProperty("userid", "");
			USER_PASS = properties.getProperty("userpass", "");
			DIALOG_ENDPOINT=properties.getProperty("dialog_endpoint", "");
			DIALOG_ID=properties.getProperty("dialog_id","");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Fired when plugin is first enabled
	@Override
    public void onEnable() {
		//getLogger().info("WatsonSpigotPlugin");
		DIALOG_SERVICE=setupDialogService(); 
		//DIALOG=setupDialog(DIALOG_SERVICE);
    }
	
   // Fired when plugin is disabled
   @Override
   public void onDisable() {
   }
    
   public boolean onCommand(CommandSender sender, Command cmd, String label,
		   					String[] args) {
	  // getLogger().info("command: " + cmd.getName());
	   //getServer().dispatchCommand(getServer().getConsoleSender(), cmd.getName());
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
			   for(int i=0; i < args.length; i++) {
				   str.append(args[i]);
				   if(i < args.length-1) {
					   str.append(" ");
				   }
			   }
			   String input = str.toString();
			   
			   String response = getResponse(input,DIALOG_SERVICE,CONVERSATION);
			   sender.sendMessage("Watson response: " + response);	
		   
			   return true;
		   }	
	   }
	   return false;
	}
   
   public DialogService setupDialogService() {
	   DialogService service = new DialogService();
	   service.setEndPoint(DIALOG_ENDPOINT);
	   service.setUsernameAndPassword
		(
				// account username from VCAP_SERVICES env variable of the Dialog service instance
				USER_ID,
				// account password from VCAP_SERVICES env variable of the Dialog service instance
				USER_PASS
		);
	   //getLogger().info("dialog service: " + service);
	   
	   return service;
   }   
  
   public Dialog setupDialog (DialogService service) {
	   return null;
   }
	
   public String getResponse(String input, DialogService service, Conversation conversation) {
   // Create conversation
	   Map<String, Object> params = new HashMap<String, Object>();
	   params.put(DialogService.DIALOG_ID, DIALOG_ID);
	   if(conversation == null) {
		   conversation = service.converse(params);
		   CONVERSATION = conversation;
	   }
	   
 
	   // Ask question
	   params.put(DialogService.CLIENT_ID, conversation.getClientId());
	   params.put(DialogService.INPUT, input);
	   params.put(DialogService.CONVERSATION_ID, conversation.getId());
	   conversation = service.converse(params);
	   return conversation.getResponse().get(0);
   }
   
   public void deleteDialog(DialogService service, Dialog dialog) {
	   if (dialog != null) {
		   service.deleteDialog(dialog.getId());
	   }
   }
   
}
