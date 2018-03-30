//returns 1 if a chunk matches the pattern
inline int chunk_match(global const char* c, const ulong x, const ulong z) {
    ulong seed = (x*341873128712LL + z*132897987541LL)^0x5DEECE66DLL;

    for(int a = 0; a < 16; ++a) {
        for(int b = 0; b < 16; ++b) {
            // this is equivalent to doing nextInt 250 times
            // it's the main reason this implementation is fast
            // I precalculated the coefficient and constant needed to advance the PRNG to the right position for generating the top-level bedrock pattern
            seed = seed*709490313259657689LL + 1748772144486964054LL;

             // get the value from the PRNG
            seed = seed & ((1LL << 48LL) - 1LL);

            if(4 <= (seed >> 17) % 5) {
                if(c[a*16+b] != 1)
                // if a comparison fails, bail out
                return 0;
            } else {
                if(c[a*16+b] != 0)
                 return 0;
            }

            // advances the PRNG a few more times to get ready for the next vertical column
            seed = seed*5985058416696778513LL + -8542997297661424380LL;
        }
    }

    return 1;
}


void print_chunk_pattern(ulong x, ulong z) {
  ulong seed = (x*341873128712LL + z*132897987541LL)^0x5DEECE66DLL;

  for(int a = 0; a < 16; ++a) {
    for(int b = 0; b < 16; ++b) {
      seed = seed*709490313259657689LL + 1748772144486964054LL;

      seed = seed & ((1LL << 48LL) - 1LL);

      if(4 <= (seed >> 17) % 5) {
        printf("1,");
      } else {
        printf("0,");
      }

      seed = seed*5985058416696778513LL + -8542997297661424380LL;
    }
    printf("%c", '\n');
  }
}


// full chunk finder
// pattern must be 16x16
kernel void bedrock_finder_fullpattern(global const char* pattern, const int start, const int end, const int step) {
    int id = get_global_id(0);

    for (int r = start + id; r < end; r += step) {
        for(int i = -r; i <= r; i++) {
            if(chunk_match(pattern, i, r)) {
                printf("chunk: (%d, %d), real: (%d, %d)\n", i, r, i*16, r*16);
            }
            if(chunk_match(pattern, i, -r)) {
                printf("chunk: (%d, %d), real: (%d, %d)\n", i, -r, i*16, (-r)*16);
            }
        }
        for(int i = -r+1; i < r; i++) {
            if(chunk_match(pattern, r, i)) {
                printf("chunk: (%d, %d), real: (%d, %d)\n", r, i, r*16, i*16);
            }
            if(chunk_match(pattern, -r, i)) {
                printf("chunk: (%d, %d), real: (%d, %d)\n", -r, i, (-r)*16, i*16);
            }
        }
    }

}
