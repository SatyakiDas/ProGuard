/* $Id: LongCpInfo.java,v 1.6 2002/05/12 14:29:08 eric Exp $
 *
 * ProGuard -- obfuscation and shrinking package for Java class files.
 *
 * Copyright (c) 1999 Mark Welsh (markw@retrologic.com)
 * Copyright (C) 2002 Eric Lafortune (eric@graphics.cornell.edu)
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package proguard.classfile;

import proguard.classfile.visitor.*;
import java.io.*;
import java.util.*;

/**
 * Representation of a 'long' entry in the ConstantPool (takes up two indices).
 *
 * @author Mark Welsh
 * @author Eric Lafortune
 */
public class LongCpInfo extends CpInfo
{
    public int u4highBytes;
    public int u4lowBytes;


    protected LongCpInfo()
    {
        super(CONSTANT_Long);
    }

    /**
     * Reads the 'info' data following the u1tag byte.
     */
    protected void readInfo(DataInput din) throws IOException
    {
        u4highBytes = din.readInt();
        u4lowBytes = din.readInt();
    }

    /**
     * Writes the 'info' data following the u1tag byte.
     */
    protected void writeInfo(DataOutput dout) throws IOException
    {
        dout.writeInt(u4highBytes);
        dout.writeInt(u4lowBytes);
    }

    /**
     * Accepts the given visitor.
     */
    public void accept(ClassFile classFile, CpInfoVisitor cpInfoVisitor)
    {
        cpInfoVisitor.visitLongCpInfo(classFile, this);
    }
}
