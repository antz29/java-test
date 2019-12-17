package com.industriallogic.henrysgroceries.provider;


import com.industriallogic.henrysgroceries.testutil.TestMockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OffersProviderTest {

    @Mock
    OffersProvider offersProvider;

    @Test
    public void getOffers() {
        when(offersProvider.getOffers()).thenReturn(TestMockUtil.getOffersList());
        assertEquals(offersProvider.getOffers(), TestMockUtil.getOffersList());
    }
}