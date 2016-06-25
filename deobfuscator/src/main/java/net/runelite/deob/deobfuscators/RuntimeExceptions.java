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

package net.runelite.deob.deobfuscators;

import java.util.ArrayList;
import net.runelite.asm.ClassFile;
import net.runelite.asm.ClassGroup;
import net.runelite.asm.Method;
import net.runelite.asm.attributes.Code;
import net.runelite.deob.Deobfuscator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeExceptions implements Deobfuscator
{
	private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptions.class);
	
	@Override
	public void run(ClassGroup group)
	{
		int i = 0;
		for (ClassFile cf : group.getClasses())
		{
			for (Method m : cf.getMethods().getMethods())
			{
				Code c = m.getCode();
				if (c == null)
					continue;
				
				for (net.runelite.asm.attributes.code.Exception e : new ArrayList<>(c.getExceptions().getExceptions()))
				{
					if (e.getCatchType() != null && e.getCatchType().getName().equals("java/lang/RuntimeException"))
					{
						c.getExceptions().remove(e);
						++i;
					}
				}
			}
		}

		logger.info("Remove {} exception handlers", i);
	}
}
