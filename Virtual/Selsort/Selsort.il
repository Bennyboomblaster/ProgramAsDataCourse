	.method public hidebysig static 
		void SelectionSort (
			int32[] arr
		) cil managed 
	{
		// Method begins at RVA 0x20b8
		// Header size: 12
		// Code size: 57 (0x39)
		.maxstack 4
		.locals init (
			[0] int32,                        // int i = 0;
			[1] int32,                        // int least = i;
			[2] int32,                        // int tmp = arr[i];
			[3] int32                         // int j = i+1;
		)

		IL_0000: ldc.i4.0                     // push constant 0
		IL_0001: stloc.0                      // set i = 0
		IL_0002: br.s IL_0032                 // jump to loop condition (outer for-loop)
		// loop start (head: IL_0032)
			IL_0004: ldloc.0                  // push i
			IL_0005: stloc.1                  // set least = i
			IL_0006: ldloc.0                  // IL_0006-IL_0009 - set j = i+1 (initialize j)
			IL_0007: ldc.i4.1                 // 
			IL_0008: add                      // 
			IL_0009: stloc.3                  // 
			IL_000a: br.s IL_001a             // jump to loop condition (inner for-loop)
			// loop start (head: IL_001a)
				IL_000c: ldarg.0              // IL_000c-IL_000e – push the element at array index [j]
				IL_000d: ldloc.3              // 
				IL_000e: ldelem.i4            //
				IL_000f: ldarg.0              // IL_000f-IL_0011 – push the element at array index [least]
				IL_0010: ldloc.1              // 
				IL_0011: ldelem.i4            // 
				IL_0012: bge.s IL_0016        // jump to IL_0016 if arr[j] is greater than or equal to arr[least]

				IL_0014: ldloc.3              // push j
				IL_0015: stloc.1              // set least = j

				IL_0016: ldloc.3              // IL_0016-IL_0019 - set j = j+1 (j++ increment)
				IL_0017: ldc.i4.1             // 
				IL_0018: add                  // 
				IL_0019: stloc.3              //

				IL_001a: ldloc.3              // push j - we use j for the comparison in j < arr.Length
				IL_001b: ldarg.0              // push argument 0 (arr)
				IL_001c: ldlen                // pop arr and push the number of elements in the array as a native unsigned int
				IL_001d: conv.i4              // convert the length of the array to a 32-bit integer
				IL_001e: blt.s IL_000c        // compare values and jump to IL_000c if j < arr.Length
			// end loop

			IL_0020: ldarg.0                  // IL_0020-IL_0022 – push the element at array index [i]
			IL_0021: ldloc.0                  // 
			IL_0022: ldelem.i4                // 
			IL_0023: stloc.2                  // set tmp = arr[i]
			IL_0024: ldarg.0                  // IL_0024-IL_0029 - replace value at arr[i] = arr[least]
			IL_0025: ldloc.0                  // 
			IL_0026: ldarg.0                  //  
			IL_0027: ldloc.1                  //  
			IL_0028: ldelem.i4                // 
			IL_0029: stelem.i4                // 
			IL_002a: ldarg.0                  // IL_002a-IL_002d - replace value at arr[least] = tmp
			IL_002b: ldloc.1                  //
			IL_002c: ldloc.2                  //
			IL_002d: stelem.i4                // 
			IL_002e: ldloc.0                  // IL_002e-IL_0031 - set i = i+1 (i++ increment)
			IL_002f: ldc.i4.1                 //
			IL_0030: add                      //
			IL_0031: stloc.0                  //

			IL_0032: ldloc.0                 // push i - we use the i for the comparison in i < arr.Length
			IL_0033: ldarg.0                 // push argument 0 (arr)
			IL_0034: ldlen                   // pop arr and push the number of elements in the array as a native unsigned int
			IL_0035: conv.i4                 // the array length is converted to 32-bit integer
			IL_0036: blt.s IL_0004           // compare values and jump to IL_0004 if i < arr.Length
		// end loop

		IL_0038: ret                         // return from method when outer loop is done
	} // end of method Selsort::SelectionSort

