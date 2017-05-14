package com.capgemini.customeronboarding.replay;

import java.util.List;

import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.customeronboarding.replay.eventhandler.ReplayEventHandler;

@RestController
public class ReplayController {
	
	@Autowired
	private ReplayingCluster replayCluster;
	
	@Autowired
	private ReplayEventHandler replayEventHandler;
	
	@RequestMapping(value = "/replay", method = RequestMethod.GET)
	@ResponseBody
	public List<String> replayEvent(){
		replayCluster.startReplay();
		return replayEventHandler.getAudit();
	}
	
	
}
