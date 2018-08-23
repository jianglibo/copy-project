package com.go2wheel.copyproject.resulthandler;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.shell.result.TerminalAwareResultHandler;

import com.go2wheel.copyproject.value.CopyDescription;
import com.go2wheel.copyproject.value.CopyDescription.COPY_STATE;
import com.go2wheel.copyproject.value.CopyResult;
import com.go2wheel.copyproject.value.CopyResult.CopyResultType;

public class CopyResultHandler extends TerminalAwareResultHandler<CopyResult> {

	private CopyResult lastCopyResult;

	@Override
	protected void doHandleResult(CopyResult result) {
		this.lastCopyResult = result;
		switch (result.getResultType()) {
		case NO_RESULT:
			terminal.writer().println("No copy result exists for now.");
			break;
		default:
			for (Entry<COPY_STATE, Long> entry : result.getCountMap().entrySet()) {
				terminal.writer().println(String.format("%s: %s", entry.getKey(), entry.getValue()));
			}
			for (String line : result.getDescribes()) {
				terminal.writer().println(line);
			}
			if (result.getResultType() == CopyResultType.DETAILED) {
				for (Entry<COPY_STATE, List<CopyDescription>> entry : result.getDescriptionMap().entrySet()) {
					terminal.writer().println(entry.getKey() + ":");
					for (CopyDescription cd : entry.getValue()) {
						terminal.writer().println(cd.getSrcRelative());
					}
				}
			}
			break;
		}
		terminal.writer().flush();
	}

	public CopyResult getLastCopyResult() {
		return lastCopyResult;
	}

}
