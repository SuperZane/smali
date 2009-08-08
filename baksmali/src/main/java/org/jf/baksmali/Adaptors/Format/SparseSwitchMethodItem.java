/*
 * [The "BSD licence"]
 * Copyright (c) 2009 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.baksmali.Adaptors.Format;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jf.dexlib.Code.Format.SparseSwitchDataPseudoInstruction;
import org.jf.dexlib.CodeItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SparseSwitchMethodItem extends InstructionFormatMethodItem<SparseSwitchDataPseudoInstruction> {
    private int baseAddress;

    public SparseSwitchMethodItem(CodeItem codeItem, int offset, StringTemplateGroup stg,
                                  SparseSwitchDataPseudoInstruction instruction, int baseAddress) {
        super(codeItem, offset, stg, instruction);
        this.baseAddress = baseAddress;
    }

    protected void setAttributes(StringTemplate template) {
        template.setAttribute("Targets", getTargets());
    }

    private static class SparseSwitchTarget {
        public int Value;
        public String Target;
    }

    private List<SparseSwitchTarget> getTargets() {
        List<SparseSwitchTarget> targets = new ArrayList<SparseSwitchTarget>();

        Iterator<SparseSwitchDataPseudoInstruction.SparseSwitchTarget> iterator = instruction.getTargets();

        while (iterator.hasNext()) {
            SparseSwitchDataPseudoInstruction.SparseSwitchTarget target = iterator.next();
            SparseSwitchTarget sparseSwitchTarget = new SparseSwitchTarget();
            sparseSwitchTarget.Value = target.value;
            sparseSwitchTarget.Target = Integer.toHexString(target.target + baseAddress);
            targets.add(sparseSwitchTarget);
        }

        return targets;
    }
}
