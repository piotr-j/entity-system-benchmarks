package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.PlainPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PlainPositionSystem extends EntityProcessingSystem {

	ComponentMapper<PlainPosition> positionMapper;

	Blackhole voidness = new Blackhole();
	
	
	@SuppressWarnings("unchecked")
	public PlainPositionSystem() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}

	@Override
	protected void process(Entity e) {
		PlainPosition pos = positionMapper.get(e);
		pos.x += 0.1f % 100000;
		pos.y -= 0.1f % 100000;
		
		voidness.consume(e);
	}
}
