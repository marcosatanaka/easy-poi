package br.com.marcosatanaka.easypoi.util;

import br.com.marcosatanaka.easypoi.util.StringToBigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class StringToBigDecimalValidationTest {

	private final String valorConverter;

	private final BigDecimal valorEsperado;

	public StringToBigDecimalValidationTest(String valorConverter, BigDecimal valorEsperado) {
		this.valorConverter = valorConverter;
		this.valorEsperado = valorEsperado;
	}

	@Parameterized.Parameters(name = "{index}_valor{0}_deve_ser_convertido_para_{1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{null, null},
				{"", null},
				{"inválido", null},
				{"R$ -", null},
				{"R$ - ", null},
				{"1", BigDecimal.ONE},
				{"10", BigDecimal.TEN},
				{"10,00", BigDecimal.TEN},
				{"10,00", BigDecimal.TEN},
				{"10.000,00", BigDecimal.valueOf(10_000)},
				{"10000,00", BigDecimal.valueOf(10_000)},
				{"R$ 1", BigDecimal.ONE},
				{"R$ 10", BigDecimal.TEN},
				{"R$ 10,00", BigDecimal.TEN},
				{"R$ 10,00", BigDecimal.TEN},
				{"R$ 10.000,00", BigDecimal.valueOf(10_000)},
				{"R$ 10000,00", BigDecimal.valueOf(10_000)},
		});
	}

	@Test
	public void deve_converter() {
		BigDecimal resultado = StringToBigDecimal.convert(valorConverter);
		if (Objects.isNull(valorEsperado)) {
			assertThat(resultado).isNull();
		} else {
			assertThat(resultado.setScale(2, ROUND_HALF_DOWN)).isEqualTo(valorEsperado.setScale(2, ROUND_HALF_DOWN));
		}
	}

}
