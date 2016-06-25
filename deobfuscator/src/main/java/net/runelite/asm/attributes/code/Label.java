/*
 * Copyright (c) 2016, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by Adam <Adam@sigterm.info>
 * 4. Neither the name of the Adam <Adam@sigterm.info> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Adam <Adam@sigterm.info> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Adam <Adam@sigterm.info> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.asm.attributes.code;

import net.runelite.asm.attributes.code.instructions.NOP;
import org.objectweb.asm.MethodVisitor;

public class Label extends NOP
{
	private org.objectweb.asm.Label label;
	
	public Label(Instructions instructions)
	{
		super(instructions);
	}

	public Label(Instructions instructions, org.objectweb.asm.Label label)
	{
		super(instructions);
		this.label = label;
	}

	@Override
	public String toString()
	{
		return "label " + next().toString();
	}

	@Override
	public void accept(MethodVisitor visitor)
	{
		visitor.visitLabel(label);
	}

	public org.objectweb.asm.Label getLabel()
	{
		return label;
	}

	public void setLabel(org.objectweb.asm.Label label)
	{
		this.label = label;
	}

	public Instruction next()
	{
		Instructions ins = this.getInstructions();
		int i = ins.getInstructions().indexOf(this);
		assert i != -1;

		Instruction next;
		do
		{
			next = ins.getInstructions().get(i + 1);
			++i;
		}
		while (next instanceof Label);

		return next;
	}
}
