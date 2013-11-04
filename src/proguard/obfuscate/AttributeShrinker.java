/* $Id: AttributeShrinker.java,v 1.6 2002/05/23 19:19:58 eric Exp $
 *
 * ProGuard -- obfuscation and shrinking package for Java class files.
 *
 * Copyright (C) 2002 Eric Lafortune (eric@graphics.cornell.edu)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package proguard.obfuscate;

import proguard.classfile.*;
import proguard.classfile.instruction.*;
import proguard.classfile.visitor.*;

import java.util.*;


/**
 * This ClassFileVisitor removes attributes that are not marked
 * as being used or required.
 *
 * @see AttributeMarker
 *
 * @author Eric Lafortune
 */
public class AttributeShrinker
  implements ClassFileVisitor,
             MemberInfoVisitor,
             AttrInfoVisitor
{
    // Implementations for ClassFileVisitor

    public void visitProgramClassFile(ProgramClassFile programClassFile)
    {
        // Compact the array for class attributes.
        programClassFile.u2attributesCount =
            shrinkArray(programClassFile.attributes,
                        programClassFile.u2attributesCount);

        // Compact the attributes in fields, methods, and class attributes,
        programClassFile.fieldsAccept(this);
        programClassFile.methodsAccept(this);
        programClassFile.attributesAccept(this);
    }


    public void visitLibraryClassFile(LibraryClassFile libraryClassFile)
    {
        // Library class files are left unchanged.
    }


    // Implementations for MemberInfoVisitor

    public void visitProgramFieldInfo(ProgramClassFile programClassFile, ProgramFieldInfo programfieldInfo)
    {
        visitMemberInfo(programClassFile, programfieldInfo);
    }


    public void visitProgramMethodInfo(ProgramClassFile programClassFile, ProgramMethodInfo programMethodInfo)
    {
        visitMemberInfo(programClassFile, programMethodInfo);
    }


    private void visitMemberInfo(ProgramClassFile programClassFile, ProgramMemberInfo programMemberInfo)
    {
        // Compact the attributes array.
        programMemberInfo.u2attributesCount =
            shrinkArray(programMemberInfo.attributes,
                        programMemberInfo.u2attributesCount);

        // Compact any attributes of the remaining attributes.
        programMemberInfo.attributesAccept(programClassFile, this);
    }


    public void visitLibraryFieldInfo(LibraryClassFile libraryClassFile, LibraryFieldInfo libraryFieldInfo)
    {
        // Library class files are left unchanged.
    }


    public void visitLibraryMethodInfo(LibraryClassFile libraryClassFile, LibraryMethodInfo libraryMethodInfo)
    {
        // Library class files are left unchanged.
    }


    // Implementations for AttrInfoVisitor

    public void visitAttrInfo(ClassFile classFile, AttrInfo attrInfo) {}
    public void visitConstantValueAttrInfo(ClassFile classFile, ConstantValueAttrInfo constantValueAttrInfo) {}
    public void visitExceptionsAttrInfo(ClassFile classFile, ExceptionsAttrInfo exceptionsAttrInfo) {}
    public void visitInnerClassesAttrInfo(ClassFile classFile, InnerClassesAttrInfo innerClassesAttrInfo) {}
    public void visitLineNumberTableAttrInfo(ClassFile classFile, LineNumberTableAttrInfo lineNumberTableAttrInfo) {}
    public void visitLocalVariableTableAttrInfo(ClassFile classFile, LocalVariableTableAttrInfo localVariableTableAttrInfo) {}
    public void visitSourceFileAttrInfo(ClassFile classFile, SourceFileAttrInfo sourceFileAttrInfo) {}
    public void visitDeprecatedAttrInfo(ClassFile classFile, DeprecatedAttrInfo deprecatedAttrInfo) {}
    public void visitSyntheticAttrInfo(ClassFile classFile, SyntheticAttrInfo syntheticAttrInfo) {}


    public void visitCodeAttrInfo(ClassFile classFile, CodeAttrInfo codeAttrInfo)
    {
        // Compact the attributes array.
        codeAttrInfo.u2attributesCount =
            shrinkArray(codeAttrInfo.attributes,
                        codeAttrInfo.u2attributesCount);
    }


    // Small utility methods.

    /**
     * Removes all VisitorAccepter objects that are not marked as being used
     * from the given array.
     * @return the new number of VisitorAccepter objects.
     */
    private static int shrinkArray(VisitorAccepter[] array, int length)
    {
        int counter = 0;

        // Shift the used objects together.
        for (int index = 0; index < length; index++)
        {
            if (AttributeUsageMarker.isUsed(array[index]))
            {
                array[counter++] = array[index];
            }
        }

        // Clear the remaining array elements.
        for (int index = counter; index < length; index++)
        {
            array[index] = null;
        }

        return counter;
    }
}
