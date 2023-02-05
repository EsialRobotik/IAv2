package actions.ax12;

import actions.ax12.actions.*;
import api.ax12.AX12;
import api.ax12.AX12Link;
import api.ax12.value.AX12Compliance;
import api.ax12.value.AX12Position;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class ActionOrchestratorHelper {
	
	public enum JSON_KEYS {
		actionId,
		actions,
		actionPools,
		actionOrchestrator,
		actionDisableTorque,
		
		actionPosition,
		actionPump,
		actionWaiting,
		actionBehaviour,
		actionSerialFlag,
		
		waitingTimeMs,
		rawAngle,
		readOnlyAngleDegrees,
		angleDegrees,
		ax12Id,
		switchState,
		pumpId,
		speed,
		acceleration,
		compliance,
		serialFlagId,
		serialFlagEnabled,
	}

	public static void serializeToJson(ActionOrchestrator ae, File destination) throws IOException {
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(destination));
		bw.write(g.toJson(actionOrchestratorToJSon(ae)));
		bw.close();
	}
	
	/**
	 * Try to unserialize an ActionOrchestrator from the given file
	 * @param ax12Link
	 * @param origin
	 * @return null if no ActionOrchestrator found in file
	 * @throws IOException
	 */
	public static ActionOrchestrator unserializeFromJson(AX12Link ax12Link, File origin) throws IOException {
		JsonReader jr = new JsonReader(new FileReader(origin));
		JsonParser jp = new JsonParser();
		try {
			JsonElement elt = jp.parse(jr);
			if (elt.isJsonObject()) {
				return actionOrchestratorFromJson(elt.getAsJsonObject(), ax12Link);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} finally {
			jr.close();
		}
		return null;
	}
	
	/**
	 * Try to unserialize an ActionOrchestrator from the given string
	 * @param ax12Link
	 * @param json
	 * @return null if no ActionOrchestrator found in file
	 * @throws IOException
	 */
	public static ActionOrchestrator unserializeFromJson(AX12Link ax12Link, String json) throws IOException {
		JsonParser jp = new JsonParser();
		try {
			JsonElement elt = jp.parse(json);			
			if (elt.isJsonObject()) {
				return actionOrchestratorFromJson(elt.getAsJsonObject(), ax12Link);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JsonObject actionOrchestratorToJSon(ActionOrchestrator ao) {
		JsonArray jsa = new JsonArray();
		int poolsCount = ao.getActionPoolCount();
		for (int i=0; i<poolsCount; i++) {
			ActionPool ap = ao.getPool(i);
			jsa.add(actionPoolToJson(ap));
		}

		JsonObject o = new JsonObject();
		o.add(JSON_KEYS.actionPools.name(), jsa);
		return o;
	}
	
	public static ActionOrchestrator actionOrchestratorFromJson(JsonObject o, AX12Link ax12Link) {
		ActionOrchestrator ao = new ActionOrchestrator();
		JsonElement pools = o.get(JSON_KEYS.actionPools.name());
		if (pools == null || !pools.isJsonArray()) {
			return ao;
		}
		
		for (JsonElement pool : pools.getAsJsonArray()) {
			if (pool.isJsonObject()) {
				ao.addActionPool(actionPoolFromJson(pool.getAsJsonObject(), ax12Link));	
			}
		}
		
		return ao;
	}
	
	public static JsonObject actionPoolToJson(ActionPool ap) {
		JsonArray jsa = new JsonArray();
		int actionCount = ap.getActionCount();
		for (int i=0; i<actionCount; i++) {
			Action a = ap.getAction(i);
			if (a instanceof AX12PositionAction) {
				jsa.add(AX12PositionActionToJson((AX12PositionAction) a));
			} else if (a instanceof PumpAction) {
				jsa.add(pumpActionToJson((PumpAction) a));
			} else if (a instanceof WaitingAction) {
				jsa.add(waitingActionToJson((WaitingAction) a));
			} else if (a instanceof BehaviourAction) {
				jsa.add(behaviourActionToJson((BehaviourAction) a));
			} else if (a instanceof AX12DisableTorqueAction) {
				jsa.add(disableTorqueActionToJson((AX12DisableTorqueAction) a));
			} else if (a instanceof SerialSignalAction) {
				jsa.add(serialSignalActionToJson((SerialSignalAction) a));
			}
		}

		JsonObject o = new JsonObject();
		o.add(JSON_KEYS.actions.name(), jsa);
		return o;
	}
	
	public static ActionPool actionPoolFromJson(JsonObject jso, AX12Link ax12Link) {
		ActionPool ap = new ActionPool();
		
		JsonElement jse = jso.get(JSON_KEYS.actions.name());
		if (jse == null || !jse.isJsonArray()) {
			return ap;
		}
		
		for (JsonElement elt : jse.getAsJsonArray()) {
			if (!elt.isJsonObject()) {
				continue;
			}
			Action a = actionFromJson(elt.getAsJsonObject(), ax12Link);
			if (a != null) {
				ap.addAction(a);
			}
		}
		
		return ap;
	}
	
	public static Action actionFromJson(JsonObject o, AX12Link ax12Link) {
		JsonElement actionIdElt = o.get(JSON_KEYS.actionId.name());
		if (actionIdElt == null || !actionIdElt.isJsonPrimitive()) {
			return null;
		}
		String actionId = actionIdElt.getAsString();
		
		if (actionId.equals(JSON_KEYS.actionPosition.name())) {
			return AX12PositionActionFromJson(o, ax12Link);
		}
		
		if (actionId.equals(JSON_KEYS.actionPump.name())) {
			return pumpActionFromJson(o, ax12Link);
		}
		
		if (actionId.equals(JSON_KEYS.actionWaiting.name())) {
			return waitingActionFromJson(o);
		}
		
		if (actionId.equals(JSON_KEYS.actionBehaviour.name())) {
			return behaviourActionFromJson(o, ax12Link);
		}
		
		if (actionId.equals(JSON_KEYS.actionDisableTorque.name())) {
			return disableTorqueActionFromJson(o, ax12Link);
		}

		if (actionId.equals(JSON_KEYS.actionSerialFlag.name())) {
			return serialSignalActionFromJson(o, ax12Link);
		}
		
		return null;
	}
	
	public static JsonObject pumpActionToJson(PumpAction pa) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionPump);
		o.add(JSON_KEYS.switchState.name(), new JsonPrimitive(pa.getEnable()));
		o.add(JSON_KEYS.pumpId.name(), new JsonPrimitive(pa.getPump().ordinal()));
		return o;
	}
	
	public static PumpAction pumpActionFromJson(JsonObject o, AX12Link ax12Link) {
		JsonElement state = o.get(JSON_KEYS.switchState.name());
		if (state == null || !state.isJsonPrimitive()) {
			return null;
		}
		JsonPrimitive p = state.getAsJsonPrimitive();
		if (!p.isBoolean()) {
			return null;
		}
		
		JsonElement pump = o.get(JSON_KEYS.pumpId.name());
		if (pump == null || !pump.isJsonPrimitive()) {
			return null;
		}
		JsonPrimitive pumpOrdinal = pump.getAsJsonPrimitive();
		if (!pumpOrdinal.isNumber()) {
			return null;
		}
		
		int ordinal = pumpOrdinal.getAsInt();
		if (ordinal < 0 || ordinal > PumpAction.PUMP.values().length) {
			return null;
		}
		
		return new PumpAction(ax12Link, PumpAction.PUMP.values()[ordinal], p.getAsBoolean());
	}
	
	public static JsonObject waitingActionToJson(WaitingAction wa) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionWaiting);
		o.add(JSON_KEYS.waitingTimeMs.name(), new JsonPrimitive(wa.getWaitingTime()));
		return o;
	}
	
	public static JsonObject behaviourActionToJson(BehaviourAction ba) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionWaiting);
		o.add(JSON_KEYS.ax12Id.name(), new JsonPrimitive(ba.getAx12().getAddress()));
		o.add(JSON_KEYS.speed.name(), new JsonPrimitive(ba.getSPeed()));
		o.add(JSON_KEYS.acceleration.name(), new JsonPrimitive(ba.getAcceleration()));
		o.add(JSON_KEYS.compliance.name(), new JsonPrimitive(ba.getCompliance().getFriendlyValue()));
		return o;
	}
	
	public static JsonObject disableTorqueActionToJson(AX12DisableTorqueAction dta) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionDisableTorque);
		o.add(JSON_KEYS.ax12Id.name(), new JsonPrimitive(dta.getAx12().getAddress()));
		return o;
	}

	public static JsonObject serialSignalActionToJson(SerialSignalAction ssa) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionSerialFlag);
		o.add(JSON_KEYS.serialFlagId.name(), new JsonPrimitive(ssa.getSignal().name()));
		o.add(JSON_KEYS.serialFlagEnabled.name(), new JsonPrimitive(ssa.getEnabled()));
		return o;
	}
	
	public static WaitingAction waitingActionFromJson(JsonObject o) {
		JsonElement elt = o.get(JSON_KEYS.waitingTimeMs.name());
		if (elt == null || !elt.isJsonPrimitive()) {
			return null;
		}
		JsonPrimitive p = elt.getAsJsonPrimitive();
		if (!p.isNumber()) {
			return null;
		}
		return new WaitingAction(p.getAsLong());
	}
	
	public static BehaviourAction behaviourActionFromJson(JsonObject o, AX12Link ax12Link) {
		try {
			int ax12Id = o.get(JSON_KEYS.ax12Id.name()).getAsInt();
			
			return new BehaviourAction(
					new AX12(ax12Id, ax12Link),
					o.get(JSON_KEYS.speed.name()).getAsInt(),
					o.get(JSON_KEYS.acceleration.name()).getAsInt(),
					AX12Compliance.fromFriendlyValue(o.get(JSON_KEYS.compliance.name()).getAsInt())
			);
		} catch (ClassCastException | IllegalStateException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static AX12DisableTorqueAction disableTorqueActionFromJson(JsonObject o, AX12Link ax12Link) {
		try {
			int ax12Id = o.get(JSON_KEYS.ax12Id.name()).getAsInt();
			
			return new AX12DisableTorqueAction(new AX12(ax12Id, ax12Link));
		} catch (ClassCastException | IllegalStateException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SerialSignalAction serialSignalActionFromJson(JsonObject o, AX12Link ax12Link) {
		try {
			String serialId  = o.get(JSON_KEYS.serialFlagId.name()).getAsString();
			boolean enabled = o.get(JSON_KEYS.serialFlagEnabled.name()).getAsBoolean();
			return new SerialSignalAction(ax12Link, SerialSignalAction.SIGNAL.valueOf(serialId), enabled);
		} catch (ClassCastException | IllegalStateException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static JsonObject AX12PositionActionToJson(AX12PositionAction apa) {
		JsonObject o = createEmtyObjectWithId(JSON_KEYS.actionPosition);
		o.add(JSON_KEYS.ax12Id.name(), new JsonPrimitive(apa.getAX12().getAddress()));
		o.add(JSON_KEYS.rawAngle.name(), new JsonPrimitive(apa.getAX12Position().getRawAngle()));
		o.add(JSON_KEYS.readOnlyAngleDegrees.name(), new JsonPrimitive(apa.getAX12Position().getAngleAsDegrees()));
		return o;
	}
	
	public static AX12PositionAction AX12PositionActionFromJson(JsonObject o, AX12Link ax12Link) {
		AX12Position angle = null;
		JsonPrimitive primAngle;
		JsonElement eltAngle = o.get(JSON_KEYS.rawAngle.name());
		JsonElement eltAngleDegrees = o.get(JSON_KEYS.angleDegrees.name());
		JsonElement eltAx12Id = o.get(JSON_KEYS.ax12Id.name());
		if (eltAx12Id == null || !eltAx12Id.isJsonPrimitive()) {
			return null;
		}
		if (eltAngleDegrees != null && eltAngleDegrees.isJsonPrimitive()) {
			primAngle = eltAngleDegrees.getAsJsonPrimitive();
			if (primAngle.isNumber()) {
				angle = AX12Position.buildFromDegrees(primAngle.getAsDouble());
			}
		}
		// La valeur brute a la priorité sur la valeur en degrés si les 2 sont déclarées
		if (eltAngle != null && eltAngle.isJsonPrimitive()) {
			primAngle = eltAngle.getAsJsonPrimitive();
			if (primAngle.isNumber()) {
				angle = AX12Position.buildFromInt(primAngle.getAsInt());
			}
		}
		if (angle == null) {
			return null;
		}
		JsonPrimitive primAx12Id = eltAx12Id.getAsJsonPrimitive();
		if (!primAx12Id.isNumber()) {
			return null;
		}
		try {
			return new AX12PositionAction(new AX12(primAx12Id.getAsInt(), ax12Link), angle);
		} catch (IllegalArgumentException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected static JsonObject createEmtyObjectWithId(JSON_KEYS e) {
		JsonObject o = new JsonObject();
		o.add(JSON_KEYS.actionId.name(), new JsonPrimitive(e.name()));
		return  o;
	}
	
}
