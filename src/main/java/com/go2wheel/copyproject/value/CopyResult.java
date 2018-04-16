package com.go2wheel.copyproject.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;

public class CopyResult {
	
	public static enum CopyResultType {
		NORMAL, DETAILED, NO_RESULT
	}
	
	private Map<COPY_STATE, Long> countMap = new HashMap<>();

	private Map<COPY_STATE, List<CopyDescription>> descriptionMap  = new HashMap<>();
	
	private CopyResultType resultType = CopyResultType.NORMAL;
	
	public static CopyResult emptyResult() {
		CopyResult cr = new CopyResult();
		cr.setResultType(CopyResultType.NO_RESULT);
		return cr;
	}
	
	public CopyResult() {
		initMap();
	}

	private void initMap() {
		for(COPY_STATE cs: COPY_STATE.values()) {
			countMap.put(cs, 0L);
			descriptionMap.put(cs, new ArrayList<>());
		}
	}
	
    public long total() {
    	return countMap.values().stream().mapToLong(l -> l).sum();
    }
        
    public void accept(CopyDescription cd) {
    	COPY_STATE thatState = cd.getState();
    	Long nl = countMap.get(thatState) + 1;
    	countMap.put(thatState, nl);
    	switch (thatState) {
		case FILE_COPY_SUCCESSED:
		case IGNORED:
		case DIR_DETECTED:
			break;
		default:
			descriptionMap.get(cd.getState()).add(cd);
		}
    }
    
    public void combine(CopyResult other) {
		for(COPY_STATE cs: COPY_STATE.values()) {
			Long selfCount = countMap.get(cs);
			Long otherCount = other.getCountMap().get(cs);
			countMap.put(cs, selfCount + otherCount);
			descriptionMap.get(cs).addAll(other.getDescriptionMap().get(cs));
		}
    }

	public Map<COPY_STATE, Long> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<COPY_STATE, Long> countMap) {
		this.countMap = countMap;
	}

	public Map<COPY_STATE, List<CopyDescription>> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<COPY_STATE, List<CopyDescription>> descriptionMap) {
		this.descriptionMap = descriptionMap;
	}

	public CopyResultType getResultType() {
		return resultType;
	}

	public void setResultType(CopyResultType resultType) {
		this.resultType = resultType;
	}
	
	public CopyResult changeResultType(CopyResultType crt) {
		this.setResultType(crt);
		return this;
	}
	
}
