/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.android.inputmethod.latin;

import java.util.Arrays;

// TODO: This class is not thread-safe.
public class ResizableIntArray {
    private int[] mArray;
    private int mLength;

    public ResizableIntArray(final int capacity) {
        reset(capacity);
    }

    public int get(final int index) {
        if (index < 0 || index >= mLength) {
            throw new ArrayIndexOutOfBoundsException("length=" + mLength + "; index=" + index);
        }
        return mArray[index];
    }

    public void add(final int index, final int val) {
        if (mLength < index + 1) {
            mLength = index;
            add(val);
        } else {
            mArray[index] = val;
        }
    }

    public void add(final int val) {
        final int nextLength = mLength + 1;
        ensureCapacity(nextLength);
        mArray[mLength] = val;
        mLength = nextLength;
    }

    private void ensureCapacity(final int minimumCapacity) {
        if (mArray.length < minimumCapacity) {
            final int nextCapacity = mArray.length * 2;
            // The following is the same as newLength =
            // Math.max(minimumCapacity, nextCapacity);
            final int newLength = minimumCapacity > nextCapacity
                    ? minimumCapacity
                    : nextCapacity;
            // TODO: Implement primitive array pool.
            mArray = Arrays.copyOf(mArray, newLength);
        }
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(final int newLength) {
        ensureCapacity(newLength);
        mLength = newLength;
    }

    public void reset(final int capacity) {
        // TODO: Implement primitive array pool.
        mArray = new int[capacity];
        mLength = 0;
    }

    public int[] getPrimitiveArray() {
        return mArray;
    }

    public void set(final ResizableIntArray ip) {
        // TODO: Implement primitive array pool.
        mArray = ip.mArray;
        mLength = ip.mLength;
    }

    public void copy(final ResizableIntArray ip) {
        // TODO: Avoid useless coping of values.
        ensureCapacity(ip.mLength);
        System.arraycopy(ip.mArray, 0, mArray, 0, ip.mLength);
        mLength = ip.mLength;
    }

    public void append(final ResizableIntArray src, final int startPos, final int length) {
        final int currentLength = mLength;
        final int newLength = currentLength + length;
        ensureCapacity(newLength);
        System.arraycopy(src.mArray, startPos, mArray, currentLength, length);
        mLength = newLength;
    }
}